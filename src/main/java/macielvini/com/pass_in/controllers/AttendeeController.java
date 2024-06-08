package macielvini.com.pass_in.controllers;

import lombok.RequiredArgsConstructor;
import macielvini.com.pass_in.dto.attendee.AttendeeBadgeResponseDto;
import macielvini.com.pass_in.services.AttendeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {
    private final AttendeeService attendeeService;

    @GetMapping("/{id}/badge")
    public ResponseEntity<AttendeeBadgeResponseDto> getAttendeeBadge(@PathVariable String id, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeBadgeResponseDto response = attendeeService.getAttendeeBadge(id, uriComponentsBuilder);
        return ResponseEntity.ok(response);
    }
}
