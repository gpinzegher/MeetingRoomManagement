package hu.mol.MeetingRoomManagement.dto;

import hu.mol.MeetingRoomManagement.model.MeetingRoom;

public record MeetingRoomDTO(String name, int capacity)
{
    public static MeetingRoomDTO from(MeetingRoom room) {
        return new MeetingRoomDTO(room.getRoomName(), room.getCapacity());
    }
}
