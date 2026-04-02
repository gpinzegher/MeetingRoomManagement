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

@ConditionalOnProperty(name = "app.mock.enabled", havingValue = "false")
@Service
public class ReservationService implements ReservationServiceInt {

    private final ReservationRepository reservationRepository;
    private final MeetingRoomService meetingRoomService;

    public ReservationService(ReservationRepository reservationRepository, MeetingRoomService meetingRoomService) {
        this.reservationRepository = reservationRepository;
        this.meetingRoomService = meetingRoomService;
    }


    public boolean isRoomAvailable(String roomName, LocalDateTime startTime, LocalDateTime endTime) {
        validateInterval(startTime, endTime);
        return reservationRepository.findOverlappingReservations(roomName, startTime, endTime).isEmpty();
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
        MeetingRoom meetingRoom = meetingRoomService.findByNameOrThrow(roomName);

        return reservationRepository.findAllByMeetingRoomOrderByStartTimeAsc(meetingRoom)
                .stream()
                .map(ReservationResponseDTO::from)
                .toList();
    }

    @Transactional
    public ReservationResponseDTO createReservation(ReservationRequestDTO request) {
        validateInterval(request.startTime(), request.endTime());

        MeetingRoom meetingRoom = meetingRoomService.findByNameOrThrow(request.roomName());
        assertRoomIsAvailable(meetingRoom, request.startTime(), request.endTime());

        Reservation reservation = reservationRepository.save(
                new Reservation(meetingRoom, request.startTime(), request.endTime())
        );

        return ReservationResponseDTO.from(reservation);
    }

    @Transactional(readOnly = true)
    private void assertRoomIsAvailable(MeetingRoom meetingRoom, LocalDateTime startTime, LocalDateTime endTime) {
        if (!reservationRepository.findOverlappingReservations(meetingRoom.getRoomName(), startTime, endTime).isEmpty()) {
            throw new ConflictException(
                    "Meeting room '%s' already has a reservation in the requested interval."
                            .formatted(meetingRoom.getRoomName())
            );
        }
    }
}
