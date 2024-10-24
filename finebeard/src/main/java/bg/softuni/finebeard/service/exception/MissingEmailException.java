package bg.softuni.finebeard.service.exception;

import org.springframework.security.core.AuthenticationException;


public class MissingEmailException extends AuthenticationException {

    public MissingEmailException(String message) {
        super(message);
    }
}
