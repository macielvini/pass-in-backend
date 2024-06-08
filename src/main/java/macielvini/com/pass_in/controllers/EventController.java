package macielvini.com.pass_in.controllers;

import lombok.RequiredArgsConstructor;
import macielvini.com.pass_in.dto.attendee.AttendeeIdDto;
import macielvini.com.pass_in.dto.attendee.AttendeeListResponseDto;
import macielvini.com.pass_in.dto.attendee.AttendeeRequestDto;
import macielvini.com.pass_in.dto.event.EventIdDto;
import macielvini.com.pass_in.dto.event.EventRequestDto;
import macielvini.com.pass_in.dto.event.EventResponseDto;
import macielvini.com.pass_in.services.AttendeeService;
import macielvini.com.pass_in.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEvent(@PathVariable String id) {
        EventResponseDto event = this.eventService.getEventDetail(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDto> createEvent(@RequestBody EventRequestDto body, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDto eventId = this.eventService.createEvent(body);
        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventId.eventId()).toUri();
        return ResponseEntity.created(uri).body(eventId);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeeListResponseDto> getEventAttendees(@PathVariable String id) {
        AttendeeListResponseDto attendeeList = this.attendeeService.getEventsAttendee(id);
        return ResponseEntity.ok(attendeeList);
    }

    @PostMapping("/{id}/attendees")
    public ResponseEntity<AttendeeIdDto> registerParticipant(@PathVariable String id, @RequestBody AttendeeRequestDto body, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeIdDto attendeeId = this.eventService.registerAttendeeOnEvent(id, body);
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId.attendeeId()).toUri();
        return ResponseEntity.created(uri).body(attendeeId);
    }
}
