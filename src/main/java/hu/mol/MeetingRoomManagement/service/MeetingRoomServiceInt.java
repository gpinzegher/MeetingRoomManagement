package hu.mol.MeetingRoomManagement.service;

import hu.mol.MeetingRoomManagement.dto.MeetingRoomResponseDTO;
import hu.mol.MeetingRoomManagement.model.MeetingRoom;

public interface MeetingRoomServiceInt {
    MeetingRoomResponseDTO createMeetingRoom(String name, int capacity);
    MeetingRoom findByNameOrThrow(String roomName);
}
