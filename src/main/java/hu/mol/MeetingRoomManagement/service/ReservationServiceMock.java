package hu.mol.MeetingRoomManagement.service;

import hu.mol.MeetingRoomManagement.dto.ReservationRequestDTO;
import hu.mol.MeetingRoomManagement.dto.ReservationResponseDTO;
import hu.mol.MeetingRoomManagement.exception.BadRequestException;
import hu.mol.MeetingRoomManagement.exception.ConflictException;
import hu.mol.MeetingRoomManagement.model.MeetingRoom;
import hu.mol.MeetingRoomManagement.model.Reservation;
import hu.mol.MeetingRoomManagement.repository.ReservationRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ConditionalOnProperty(name = "app.mock.enabled", havingValue = "true")
@Service
public class ReservationServiceMock implements ReservationServiceInt {

    private boolean isRoomAvailable = true;
    private ReservationResponseDTO reservationResponse1 = new ReservationResponseDTO(1L, "Mock Room 1", LocalDateTime.parse("2026-04-02T14:30:00"), LocalDateTime.parse("2026-04-02T15:00:00"));
    private ReservationResponseDTO reservationResponse2 = new ReservationResponseDTO(1L, "Mock Room 2", LocalDateTime.parse("2026-04-02T15:30:00"), LocalDateTime.parse("2026-04-02T16:30:00"));
    private List<ReservationResponseDTO> reservationList = List.of(reservationResponse1, reservationResponse2);

    public ReservationServiceMock() {}


    public boolean isRoomAvailable(String roomName, LocalDateTime startTime, LocalDateTime endTime) {
        validateInterval(startTime, endTime);
        return isRoomAvailable;
    }

    private void validateInterval(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BadRequestException("Start time and end time are required.");
        }
        if (!endTime.isAfter(startTime)) {
            throw new BadRequestException("End time must be after start time.");
        }
    }

    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> listReservations(String roomName) {
        return reservationList;
    }

    @Transactional
    public ReservationResponseDTO createReservation(ReservationRequestDTO request) {
        validateInterval(request.startTime(), request.endTime());
        return reservationResponse1;
    }
}
