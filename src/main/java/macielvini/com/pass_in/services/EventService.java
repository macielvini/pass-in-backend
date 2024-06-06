package macielvini.com.pass_in.services;

import lombok.RequiredArgsConstructor;
import macielvini.com.pass_in.domain.attendee.Attendee;
import macielvini.com.pass_in.domain.event.Event;
import macielvini.com.pass_in.dto.event.EventResponseDto;
import macielvini.com.pass_in.repositories.AttendeeRepository;
import macielvini.com.pass_in.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;

    public EventResponseDto getEventDetail(String id) {
        Event event = this.eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found with ID: " + id));
        List<Attendee> attendeeList = this.attendeeRepository.findByEventId(id);
        return new EventResponseDto(event, attendeeList.size());
    }
}
