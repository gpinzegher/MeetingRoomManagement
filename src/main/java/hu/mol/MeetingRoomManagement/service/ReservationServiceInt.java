package hu.mol.MeetingRoomManagement.service;

import hu.mol.MeetingRoomManagement.dto.ReservationRequestDTO;
import hu.mol.MeetingRoomManagement.dto.ReservationResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationServiceInt {
    ReservationResponseDTO createReservation(ReservationRequestDTO request);
    List<ReservationResponseDTO> listReservations(String roomName);
    boolean isRoomAvailable(String roomName, LocalDateTime startTime, LocalDateTime endTime);
}
