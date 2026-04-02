package hu.mol.MeetingRoomManagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "MEETING_ROOMS")
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ROOM_NAME", nullable = false, length = 100)
    private String roomName;

    @Column(name = "CAPACITY", nullable = false)
    private int capacity;

    public MeetingRoom() {
    }

    public MeetingRoom(String roomName, int capacity) {
        this.roomName = roomName;
        this.capacity = capacity;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }
}
