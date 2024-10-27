package bg.softuni.finebeard.service.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when an attempt is made to register a user that already exists in the system.
 */
public class UserAlreadyExistsException extends AuthenticationException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
