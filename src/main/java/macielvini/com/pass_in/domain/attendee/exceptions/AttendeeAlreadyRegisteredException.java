package macielvini.com.pass_in.domain.attendee.exceptions;

public class AttendeeAlreadyRegisteredException extends RuntimeException {
    public AttendeeAlreadyRegisteredException(String message) {
        super(message);
    }
}
