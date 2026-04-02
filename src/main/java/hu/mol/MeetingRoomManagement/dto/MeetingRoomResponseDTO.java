package hu.mol.MeetingRoomManagement.dto;

import hu.mol.MeetingRoomManagement.model.MeetingRoom;

public record MeetingRoomResponseDTO(Long id, String name, int capacity)
{
    public static MeetingRoomResponseDTO from(MeetingRoom room) {
        return new MeetingRoomResponseDTO(room.getId(), room.getRoomName(), room.getCapacity());
    }
}
