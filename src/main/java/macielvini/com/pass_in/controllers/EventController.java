package macielvini.com.pass_in.controllers;

import lombok.RequiredArgsConstructor;
import macielvini.com.pass_in.dto.event.EventIdDto;
import macielvini.com.pass_in.dto.event.EventRequestDto;
import macielvini.com.pass_in.dto.event.EventResponseDto;
import macielvini.com.pass_in.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

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


}
