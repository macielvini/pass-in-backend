package macielvini.com.pass_in.controllers;

import lombok.RequiredArgsConstructor;
import macielvini.com.pass_in.dto.attendee.AttendeeBadgeResponseDto;
import macielvini.com.pass_in.services.AttendeeService;
import macielvini.com.pass_in.services.CheckInService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {
    private final AttendeeService attendeeService;
    private final CheckInService checkInService;

    @GetMapping("/{id}/badge")
    public ResponseEntity<AttendeeBadgeResponseDto> getAttendeeBadge(@PathVariable String id, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeBadgeResponseDto response = attendeeService.getAttendeeBadge(id, uriComponentsBuilder);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        this.attendeeService.checkInAttendee(attendeeId);
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();
        return ResponseEntity.created(uri).build();
    }
}
