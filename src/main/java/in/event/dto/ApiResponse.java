package in.event.dto;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("prototype")
public class ApiResponse {
    private String status;
    private String message;
    private Object data;
}
