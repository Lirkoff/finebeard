package bg.softuni.finebeard.service.oauth;

import bg.softuni.finebeard.service.exception.MissingEmailException;
import bg.softuni.finebeard.service.exception.UserAlreadyExistsException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * OAuthFailureHandler handles authentication failures during OAuth2 login attempts.
 *
 * This class is a Spring Component that implements the AuthenticationFailureHandler interface
 * to handle different types of authentication exceptions, redirecting the user to appropriate
 * error pages based on the specific exception type.
 */
@Component
public class OAuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof MissingEmailException) {
            response.sendRedirect("/users/email-required");
        } else if (exception instanceof UserAlreadyExistsException){
            response.sendRedirect("/users/existing-user");
        } else {
            response.sendRedirect("/login?error");
        }


    }
}
