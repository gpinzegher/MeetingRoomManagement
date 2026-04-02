package hu.mol.MeetingRoomManagement.dto;

import hu.mol.MeetingRoomManagement.model.Reservation;

import java.time.LocalDateTime;

public record ReservationResponseDTO(
        Long id,
        String roomName,
        LocalDateTime startTime,
        LocalDateTime endTime
) {

    public static ReservationResponseDTO from(Reservation reservation) {
        return new ReservationResponseDTO(
                reservation.getId(),
                reservation.getMeetingRoom().getRoomName(),
                reservation.getStartTime(),
                reservation.getEndTime()
        );
    }
}
