package hu.mol.MeetingRoomManagement.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVATIONS")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meeting_room_id", nullable = false)
    private MeetingRoom meetingRoom;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    protected Reservation() {
    }

    public Reservation(MeetingRoom meetingRoom, LocalDateTime startTime, LocalDateTime endTime) {
        this.meetingRoom = meetingRoom;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
