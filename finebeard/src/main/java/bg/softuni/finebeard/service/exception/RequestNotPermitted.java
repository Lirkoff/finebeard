package bg.softuni.finebeard.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RequestNotPermitted extends RuntimeException {
    public RequestNotPermitted(String message) {
        super(message);
    }
}