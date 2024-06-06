package macielvini.com.pass_in.repositories;

import macielvini.com.pass_in.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
