package in.event.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TaskPayload {
    private String name;
    private Date deadline;
    private String status;
    private Long eventId;
    private Long attendeeId;
}
