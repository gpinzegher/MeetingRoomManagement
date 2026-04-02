package hu.mol.MeetingRoomManagement;

import hu.mol.MeetingRoomManagement.model.MeetingRoom;
import hu.mol.MeetingRoomManagement.model.Reservation;
import hu.mol.MeetingRoomManagement.repository.MeetingRoomRepository;
import hu.mol.MeetingRoomManagement.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class MeetingRoomManagementApiIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void cleanDatabase() {
        reservationRepository.deleteAll();
        meetingRoomRepository.deleteAll();
    }

    @Test
    void shouldCreateMeetingRoomWhenNameIsUnique() throws Exception {
        mockMvc.perform(post("/api/meeting-rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(meetingRoomJson("Room1")))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/meeting-rooms/Room1")))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Room1"));
    }

    @Test
    void shouldRejectDuplicateMeetingRoomNamesIgnoringCase() throws Exception {
        meetingRoomRepository.save(new MeetingRoom("Room1", 10));

        mockMvc.perform(post("/api/meeting-rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(meetingRoomJson("room1")))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Meeting room with name 'room1' already exists."));
    }

    @Test
    void shouldCreateReservationAndRejectOverlapForSameRoom() throws Exception {
        meetingRoomRepository.save(new MeetingRoom("Room1", 10));

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson("Room1", "2026-04-10T10:00:00", "2026-04-10T11:00:00")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomName").value("Room1"));

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson("Room1", "2026-04-10T10:30:00", "2026-04-10T11:30:00")))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Meeting room 'Room1' already has a reservation in the requested interval."));
    }

    @Test
    void shouldReturnAvailabilityForRequestedInterval() throws Exception {
        MeetingRoom meetingRoom = meetingRoomRepository.save(new MeetingRoom("Room1", 10));
        reservationRepository.save(new Reservation(
                meetingRoom,
                LocalDateTime.parse("2026-04-10T10:00:00"),
                LocalDateTime.parse("2026-04-10T11:00:00")
        ));

        mockMvc.perform(get("/api/meeting-rooms/{roomName}/availability", "Room1")
                        .param("startTime", "2026-04-10T10:15:00")
                        .param("endTime", "2026-04-10T10:45:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").value("Room1"))
                .andExpect(jsonPath("$.available").value(false));

        mockMvc.perform(get("/api/meeting-rooms/{roomName}/availability", "Room1")
                        .param("startTime", "2026-04-10T11:00:00")
                        .param("endTime", "2026-04-10T12:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    void shouldListReservationsByMeetingRoomOrderedByStartTime() throws Exception {
        MeetingRoom room1 = meetingRoomRepository.save(new MeetingRoom("Room1", 10));
        MeetingRoom room2 = meetingRoomRepository.save(new MeetingRoom("Room2", 10));

        reservationRepository.save(new Reservation(
                room1,
                LocalDateTime.parse("2026-04-10T14:00:00"),
                LocalDateTime.parse("2026-04-10T15:00:00")
        ));
        reservationRepository.save(new Reservation(
                room1,
                LocalDateTime.parse("2026-04-10T09:00:00"),
                LocalDateTime.parse("2026-04-10T10:00:00")
        ));
        reservationRepository.save(new Reservation(
                room2,
                LocalDateTime.parse("2026-04-10T09:00:00"),
                LocalDateTime.parse("2026-04-10T10:00:00")
        ));

        mockMvc.perform(get("/api/meeting-rooms/{roomName}/reservations", "Room1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].roomName").value("Room1"))
                .andExpect(jsonPath("$[0].startTime").value("2026-04-10T09:00:00"))
                .andExpect(jsonPath("$[1].startTime").value("2026-04-10T14:00:00"));
    }

    @Test
    void shouldRejectReservationWithInvalidInterval() throws Exception {
        meetingRoomRepository.save(new MeetingRoom("Room1", 10));

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson("Room1", "2026-04-10T12:00:00", "2026-04-10T11:00:00")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("End time must be after start time."));
    }

    private String meetingRoomJson(String roomName) {
        return """
				{
				  "name": "%s"
				}
				""".formatted(roomName);
    }

    private String reservationJson(String roomName, String startTime, String endTime) {
        return """
				{
				  "roomName": "%s",
				  "startTime": "%s",
				  "endTime": "%s"
				}
				""".formatted(roomName, startTime, endTime);
    }
}
