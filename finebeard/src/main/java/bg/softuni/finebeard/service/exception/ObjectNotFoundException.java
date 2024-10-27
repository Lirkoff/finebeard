package bg.softuni.finebeard.service.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to be thrown when an object is not found.
 *
 * <p>The exception is annotated with {@link ResponseStatus} to set the HTTP status code
 * to {@link HttpStatus#NOT_FOUND NOT_FOUND} (404) when the exception is thrown.</p>
 *
 * <p>This exception extends {@link RuntimeException} and is intended to be used in
 * scenarios where an object requested by the user does not exist in the system.</p>
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
