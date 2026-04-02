package hu.mol.MeetingRoomManagement.repository;

import hu.mol.MeetingRoomManagement.model.MeetingRoom;
import hu.mol.MeetingRoomManagement.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.meetingRoom.roomName = :roomName " +
            "AND (:start BETWEEN r.startTime AND r.endTime " +
            "OR :end BETWEEN r.startTime AND r.endTime)")
    List<Reservation> findOverlappingReservations(
            @Param("roomName") String roomName,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    List<Reservation> findAllByMeetingRoomOrderByStartTimeAsc(MeetingRoom meetingRoom);
}
