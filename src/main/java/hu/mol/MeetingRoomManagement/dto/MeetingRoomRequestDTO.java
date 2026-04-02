package hu.mol.MeetingRoomManagement.dto;

import jakarta.validation.constraints.NotBlank;

public record MeetingRoomRequestDTO(
        @NotBlank(message = "Room name is required.")
        String name,
        int capacity
){}
