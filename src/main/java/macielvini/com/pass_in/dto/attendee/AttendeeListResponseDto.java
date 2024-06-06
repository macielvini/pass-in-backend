package macielvini.com.pass_in.dto.attendee;

import lombok.Getter;

import java.util.List;

@Getter
public record AttendeeListResponseDto(List<AttendeeDetail> attendees) {
}
