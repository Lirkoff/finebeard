package bg.softuni.finebeard.service.oauth;

import bg.softuni.finebeard.model.enums.AuthProvider;
import bg.softuni.finebeard.repository.UserRepository;
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


@Component
public class OAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final UserService userService;
    private final UserRepository userRepository;

    public OAuthSuccessHandler(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
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
