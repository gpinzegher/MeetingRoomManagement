package hu.mol.MeetingRoomManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationRequestDTO(
        @NotBlank(message = "Room name is required.")
        String roomName,
        @NotNull(message = "Start time is required.")
        LocalDateTime startTime,
        @NotNull(message = "End time is required.")
        LocalDateTime endTime
) { }
