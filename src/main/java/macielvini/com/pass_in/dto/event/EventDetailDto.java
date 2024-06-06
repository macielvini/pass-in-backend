package macielvini.com.pass_in.dto.event;

public record EventDetailDto(String id,
                             String title,
                             String detail,
                             String slug,
                             Integer maximumAttendees,
                             Integer attendeesAmount) {
}
