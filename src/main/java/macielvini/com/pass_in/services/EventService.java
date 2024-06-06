package macielvini.com.pass_in.services;

import lombok.RequiredArgsConstructor;
import macielvini.com.pass_in.domain.attendee.Attendee;
import macielvini.com.pass_in.domain.event.Event;
import macielvini.com.pass_in.domain.event.exceptions.EventNotFoundException;
import macielvini.com.pass_in.dto.event.EventIdDto;
import macielvini.com.pass_in.dto.event.EventRequestDto;
import macielvini.com.pass_in.dto.event.EventResponseDto;
import macielvini.com.pass_in.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDto getEventDetail(String id) {
        Event event = this.eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + id));
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

    private String createSlug(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized
                .replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("[\\s+]", "-")
                .toLowerCase();

    }
}
