package bg.softuni.finebeard.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating that a request was not permitted due to exceeding defined rate limits.
 * This exception results in a response with HTTP status code 429 (Too Many Requests).
 */
@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RequestNotPermitted extends RuntimeException {
    public RequestNotPermitted(String message) {
        super(message);
    }
}