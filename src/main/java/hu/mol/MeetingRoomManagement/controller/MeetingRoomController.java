package hu.mol.MeetingRoomManagement.controller;

import hu.mol.MeetingRoomManagement.dto.MeetingRoomRequestDTO;
import hu.mol.MeetingRoomManagement.dto.MeetingRoomResponseDTO;
import hu.mol.MeetingRoomManagement.dto.ReservationResponseDTO;
import hu.mol.MeetingRoomManagement.service.MeetingRoomService;
import hu.mol.MeetingRoomManagement.service.MeetingRoomServiceInt;
import hu.mol.MeetingRoomManagement.service.ReservationService;
import hu.mol.MeetingRoomManagement.service.ReservationServiceInt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/meeting-rooms")
@Tag(name = "Meeting Rooms")
public class MeetingRoomController {

    private final MeetingRoomServiceInt meetingRoomService;
    private final ReservationServiceInt reservationService;

    public MeetingRoomController(MeetingRoomServiceInt meetingRoomService, ReservationServiceInt reservationService) {
        this.meetingRoomService = meetingRoomService;
        this.reservationService = reservationService;
    }

    @PostMapping
    @Operation(summary = "Create a new meeting room")
    public ResponseEntity<MeetingRoomResponseDTO> createMeetingRoom(@Valid @RequestBody MeetingRoomRequestDTO request) {
        MeetingRoomResponseDTO response = meetingRoomService.createMeetingRoom(request.name(), request.capacity());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomName}/availability")
    @Operation(summary = "Check the availability of a meeting room")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable String roomName,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        boolean isAvailable = reservationService.isRoomAvailable(roomName, startTime, endTime);
        return ResponseEntity.ok(isAvailable);
    }

    @GetMapping("/{roomName}/reservations")
    @Operation(summary = "List reservations by meeting room")
    public List<ReservationResponseDTO> listReservations(@PathVariable String roomName) {
        return reservationService.listReservations(roomName);
    }

}
