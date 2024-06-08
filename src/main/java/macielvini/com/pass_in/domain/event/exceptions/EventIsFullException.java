package macielvini.com.pass_in.domain.event.exceptions;

public class EventIsFullException extends RuntimeException {
    public EventIsFullException(String message) {
        super(message);
    }
}
