package hu.mol.MeetingRoomManagement.service;

import hu.mol.MeetingRoomManagement.dto.MeetingRoomDTO;
import hu.mol.MeetingRoomManagement.exception.BadRequestException;
import hu.mol.MeetingRoomManagement.exception.ConflictException;
import hu.mol.MeetingRoomManagement.model.MeetingRoom;
import hu.mol.MeetingRoomManagement.repository.MeetingRoomRepository;
import org.springframework.stereotype.Service;

@Service
public class MeetingRoomService {

    private final MeetingRoomRepository repository;

    public MeetingRoomService(MeetingRoomRepository repository) {
        this.repository = repository;
    }

    public MeetingRoomDTO createMeetingRoom(String name, int capacity) {
        String normalizedName = normalizeRoomName(name);

        if (repository.existsByNameIgnoreCase(normalizedName)) {
            throw new ConflictException("Meeting room with name '%s' already exists.".formatted(normalizedName));
        }

        MeetingRoom meetingRoom = repository.save(new MeetingRoom(normalizedName, capacity));
        return MeetingRoomDTO.from(meetingRoom);
    }

    private String normalizeRoomName(String roomName) {
        if (roomName == null || roomName.isBlank()) {
            throw new BadRequestException("Room name is required");
        }
        return roomName.trim();
    }
}
