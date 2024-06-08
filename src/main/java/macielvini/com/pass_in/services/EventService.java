package macielvini.com.pass_in.services;

import lombok.RequiredArgsConstructor;
import macielvini.com.pass_in.domain.attendee.Attendee;
import macielvini.com.pass_in.domain.event.Event;
import macielvini.com.pass_in.domain.event.exceptions.EventIsFullException;
import macielvini.com.pass_in.domain.event.exceptions.EventNotFoundException;
import macielvini.com.pass_in.dto.attendee.AttendeeIdDto;
import macielvini.com.pass_in.dto.attendee.AttendeeRequestDto;
import macielvini.com.pass_in.dto.event.EventIdDto;
import macielvini.com.pass_in.dto.event.EventRequestDto;
import macielvini.com.pass_in.dto.event.EventResponseDto;
import macielvini.com.pass_in.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public AttendeeIdDto registerAttendeeOnEvent(String eventId, AttendeeRequestDto attendeeDto) {
        Event event = this.getEventById(eventId);

        this.attendeeService.verifyAttendeeSubscription(attendeeDto.email(), eventId);

        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if (event.getMaximumAttendees() <= attendeeList.size()) {
            throw new EventIsFullException("Maximum number of attendees reached");
        }

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeDto.name());
        newAttendee.setEmail(attendeeDto.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());

        this.attendeeService.registerAttendee(newAttendee);
        return new AttendeeIdDto(newAttendee.getId());
    }

    public EventResponseDto getEventDetail(String id) {
        Event event = this.getEventById(id);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(id);
        return new EventResponseDto(event, attendeeList.size());
    }

    public EventIdDto createEvent(EventRequestDto eventDto) {
        Event event = new Event();
        event.setTitle(eventDto.title());
        event.setDetail(eventDto.detail());
        event.setMaximumAttendees(eventDto.maximumAttendees());
        event.setSlug(this.createSlug(eventDto.title()));
        this.eventRepository.save(event);
        return new EventIdDto(event.getId());
    }

    private Event getEventById(String eventId) {
        return this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }

    private String createSlug(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized
                .replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("[\\s+]", "-")
                .toLowerCase();

    }
}
