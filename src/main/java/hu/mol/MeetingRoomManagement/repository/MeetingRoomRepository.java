package hu.mol.MeetingRoomManagement.repository;

import hu.mol.MeetingRoomManagement.model.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
    boolean existsByNameIgnoreCase(String name);
}
