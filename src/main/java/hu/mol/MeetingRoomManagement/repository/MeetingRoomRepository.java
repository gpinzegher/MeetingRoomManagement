package hu.mol.MeetingRoomManagement.repository;

import hu.mol.MeetingRoomManagement.model.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {

    boolean existsByRoomNameIgnoreCase(String name);

    Optional<MeetingRoom> findByRoomNameIgnoreCase(String name);
}
