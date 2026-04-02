package hu.mol.MeetingRoomManagement.controller;

import hu.mol.MeetingRoomManagement.dto.MeetingRoomDTO;
import hu.mol.MeetingRoomManagement.service.MeetingRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/meeting-rooms")
@Tag(name = "Meeting Rooms")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @PostMapping
    @Operation(summary = "Create a new meeting room")
    public ResponseEntity<MeetingRoomDTO> createMeetingRoom(@Valid @RequestBody MeetingRoomDTO request) {
        MeetingRoomDTO response = meetingRoomService.createMeetingRoom(request.name(), request.capacity());
        return ResponseEntity.ok(response);
    }
}
