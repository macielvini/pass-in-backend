package macielvini.com.pass_in.services;

import lombok.RequiredArgsConstructor;
import macielvini.com.pass_in.domain.attendee.Attendee;
import macielvini.com.pass_in.domain.checkIn.CheckIn;
import macielvini.com.pass_in.domain.checkIn.exceptions.CheckInAlreadyExistsException;
import macielvini.com.pass_in.repositories.CheckInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee) {
        this.verifyCheckInExists(attendee.getId());

        CheckIn checkIn = new CheckIn();
        checkIn.setAttendee(attendee);
        checkIn.setCreatedAt(LocalDateTime.now());
        this.checkInRepository.save(checkIn);
    }

    public Optional<CheckIn> getCheckIn(String attendeeId) {
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }

    private void verifyCheckInExists(String attendeeId) {
        Optional<CheckIn> checkIn = this.getCheckIn(attendeeId);
        if (checkIn.isPresent()) {
            throw new CheckInAlreadyExistsException("Attendee already checked in");
        }
    }
}
