package macielvini.com.pass_in.config;

import macielvini.com.pass_in.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import macielvini.com.pass_in.domain.attendee.exceptions.AttendeeNotFoundException;
import macielvini.com.pass_in.domain.checkIn.exceptions.CheckInAlreadyExistsException;
import macielvini.com.pass_in.domain.event.exceptions.EventIsFullException;
import macielvini.com.pass_in.domain.event.exceptions.EventNotFoundException;
import macielvini.com.pass_in.dto.general.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEventNotFound(EventNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(EventIsFullException.class)
    public ResponseEntity<ErrorResponseDto> handleEventIsFull(EventIsFullException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleAttendeeNotFound(AttendeeNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(AttendeeAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponseDto> handleAttendeeAlreadyExists(AttendeeAlreadyRegisteredException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCheckInAlreadyExists(CheckInAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDto(exception.getMessage()));
    }
}
