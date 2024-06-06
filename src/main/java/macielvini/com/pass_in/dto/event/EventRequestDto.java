package macielvini.com.pass_in.dto.event;

public record EventRequestDto(
        String title,
        String detail,
        Integer maximumAttendees
) {
}
