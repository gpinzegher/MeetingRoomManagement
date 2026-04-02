package hu.mol.MeetingRoomManagement.controller;

import hu.mol.MeetingRoomManagement.dto.ReservationRequestDTO;
import hu.mol.MeetingRoomManagement.dto.ReservationResponseDTO;
import hu.mol.MeetingRoomManagement.service.ReservationService;
import hu.mol.MeetingRoomManagement.service.ReservationServiceInt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservations")
public class ReservationController {

    private final ReservationServiceInt service;

    public ReservationController(ReservationServiceInt service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a reservation for a meeting room")
    public ResponseEntity<ReservationResponseDTO> createReservation(@Valid @RequestBody ReservationRequestDTO request) {
        ReservationResponseDTO response = service.createReservation(request);
        return ResponseEntity.ok(response);
    }
}
