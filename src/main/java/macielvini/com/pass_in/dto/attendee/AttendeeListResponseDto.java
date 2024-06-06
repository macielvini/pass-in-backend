package macielvini.com.pass_in.dto.attendee;

import lombok.Getter;

import java.util.List;

public record AttendeeListResponseDto(List<AttendeeDetail> attendees) {
}
