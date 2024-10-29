package bg.softuni.finebeard.service.oauth;

import bg.softuni.finebeard.model.enums.AuthProvider;
import bg.softuni.finebeard.service.UserService;
import bg.softuni.finebeard.service.exception.MissingEmailException;

import bg.softuni.finebeard.service.exception.UserAlreadyExistsException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;


/**
 * This class handles the successful OAuth2 authentication process.
 *
 * Extends SavedRequestAwareAuthenticationSuccessHandler to provide custom behavior upon
 * successful authentication, specifically handling OAuth2 authentication tokens.
 *
 * The onAuthenticationSuccess method:
 * - Retrieves user details from the provided OAuth2 token.
 * - Creates a new user if they do not exist.
 * - Logs in the user if they already exist.
 * - Throws exceptions if necessary information (like email) is missing or user already exists.
 */
@Component
public class OAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final UserService userService;

    public OAuthSuccessHandler(UserService userService){
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken token) {

            OAuth2User user = token.getPrincipal();

            String providerId = user.getAttribute("id").toString();
            String email = user.getAttribute("email");
            String names = user.getAttribute("name");
            AuthProvider authProvider = AuthProvider.GITHUB;


            if (email == null || email.isEmpty()) {
                authentication.setAuthenticated(false);
                throw new MissingEmailException("Email is required for login");
            } else {
                try {
                    userService.createUserIfDoesNotExist(email, names, providerId, authProvider);
                    userService.login(email);
                } catch (UserAlreadyExistsException e) {
                    authentication.setAuthenticated(false);
                    throw new UserAlreadyExistsException("User already exists");
                }

            }


        }

        super.onAuthenticationSuccess(request, response, authentication);
    }


}
