package macielvini.com.pass_in.repositories;

import macielvini.com.pass_in.domain.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {
}
