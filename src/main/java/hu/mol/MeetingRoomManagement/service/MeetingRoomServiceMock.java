package hu.mol.MeetingRoomManagement.service;

import hu.mol.MeetingRoomManagement.dto.MeetingRoomResponseDTO;
import hu.mol.MeetingRoomManagement.exception.BadRequestException;
import hu.mol.MeetingRoomManagement.exception.ConflictException;
import hu.mol.MeetingRoomManagement.exception.ResourceNotFoundException;
import hu.mol.MeetingRoomManagement.model.MeetingRoom;
import hu.mol.MeetingRoomManagement.repository.MeetingRoomRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@ConditionalOnProperty(name = "app.mock.enabled", havingValue = "true")
@Service
public class MeetingRoomServiceMock implements MeetingRoomServiceInt {

    private final MeetingRoom meetingRoom = new MeetingRoom("Mock Room 1", 10);
    private final MeetingRoomResponseDTO response = new MeetingRoomResponseDTO(1L,"Mock Room 1", 10);

    public MeetingRoomServiceMock() {}

    public MeetingRoomResponseDTO createMeetingRoom(String name, int capacity) {
        return response;
    }

    private String normalizeRoomName(String roomName) {
        if (roomName == null || roomName.isBlank()) {
            throw new BadRequestException("Room name is required");
        }
        return roomName.trim();
    }

    public MeetingRoom findByNameOrThrow(String roomName) {
        String normalizedName = normalizeRoomName(roomName);
        return meetingRoom;
    }
}
