package bg.softuni.finebeard.service.exception;

import org.springframework.security.core.AuthenticationException;


/**
 * This exception is thrown when an authentication attempt is made and the required email
 * is missing.
 *
 * Typically used to indicate that an email must be present for authentication
 * to succeed, and if it's not, this exception will be thrown, usually by the
 * {@link org.springframework.security.web.authentication.AuthenticationFailureHandler}.
 */
public class MissingEmailException extends AuthenticationException {

    public MissingEmailException(String message) {
        super(message);
    }
}
