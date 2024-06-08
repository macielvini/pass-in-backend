package macielvini.com.pass_in.services;

import lombok.RequiredArgsConstructor;
import macielvini.com.pass_in.domain.attendee.Attendee;
import macielvini.com.pass_in.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import macielvini.com.pass_in.domain.attendee.exceptions.AttendeeNotFoundException;
import macielvini.com.pass_in.domain.checkIn.CheckIn;
import macielvini.com.pass_in.dto.attendee.AttendeeBadgeResponseDto;
import macielvini.com.pass_in.dto.attendee.AttendeeDetail;
import macielvini.com.pass_in.dto.attendee.AttendeeListResponseDto;
import macielvini.com.pass_in.dto.attendee.AttendeeBadgeDto;
import macielvini.com.pass_in.repositories.AttendeeRepository;
import macielvini.com.pass_in.repositories.CheckInRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;

    public AttendeeBadgeResponseDto getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = this.getAttendeeById(attendeeId);

        var uri = uriComponentsBuilder.path("/attendees/{id}/check-in").buildAndExpand(attendee.getId()).toUri().toString();

        return new AttendeeBadgeResponseDto(
                new AttendeeBadgeDto(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getId())
        );
    }

    public void verifyAttendeeSubscription(String email, String eventId) {
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);

        if (isAttendeeRegistered.isPresent()) {
            throw new AttendeeAlreadyRegisteredException("Attendee already registered");
        }
    }

    public Attendee registerAttendee(Attendee attendee) {
        this.attendeeRepository.save(attendee);
        return attendee;
    }

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeeListResponseDto getEventsAttendee(String eventId) {
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);
        List<AttendeeDetail> attendeeDetails = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetail(
                    attendee.getId(),
                    attendee.getName(),
                    attendee.getEmail(),
                    attendee.getCreatedAt(),
                    checkedInAt);
        }).toList();
        return new AttendeeListResponseDto(attendeeDetails);
    }

    private Attendee getAttendeeById(String id) {
        return this.attendeeRepository.findById(id).orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID: " + id));
    }
}
