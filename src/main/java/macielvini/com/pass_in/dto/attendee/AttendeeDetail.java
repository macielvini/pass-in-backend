package macielvini.com.pass_in.dto.attendee;

import java.time.LocalDateTime;

public record AttendeeDetail(
        String id,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime checkedInAt
) {
}
