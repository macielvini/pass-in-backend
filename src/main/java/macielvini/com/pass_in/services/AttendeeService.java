package macielvini.com.pass_in.services;

import lombok.RequiredArgsConstructor;
import macielvini.com.pass_in.domain.attendee.Attendee;
import macielvini.com.pass_in.domain.checkIn.CheckIn;
import macielvini.com.pass_in.dto.attendee.AttendeeDetail;
import macielvini.com.pass_in.dto.attendee.AttendeeListResponseDto;
import macielvini.com.pass_in.repositories.AttendeeRepository;
import macielvini.com.pass_in.repositories.CheckInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final CheckInRepository checkInRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeeListResponseDto getEventsAttendee(String eventId) {
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);
        List<AttendeeDetail> attendeeDetails = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());
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
}
