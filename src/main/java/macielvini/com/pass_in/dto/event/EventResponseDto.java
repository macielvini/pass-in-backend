package macielvini.com.pass_in.dto.event;

import lombok.Getter;
import macielvini.com.pass_in.domain.event.Event;

@Getter
public class EventResponseDto {
    EventDetailDto event;

    public EventResponseDto(Event event, Integer numberOfAttendees) {
        this.event = new EventDetailDto(
                event.getId(),
                event.getTitle(),
                event.getDetail(),
                event.getSlug(),
                event.getMaximumAttendees(),
                numberOfAttendees);
    }
}
