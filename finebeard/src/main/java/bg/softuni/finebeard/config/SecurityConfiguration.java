package bg.softuni.finebeard.config;


import bg.softuni.finebeard.repository.UserRepository;
import bg.softuni.finebeard.service.impl.FinebeardUserDetailsService;
import bg.softuni.finebeard.service.oauth.OAuthFailureHandler;
import bg.softuni.finebeard.service.oauth.OAuthSuccessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for setting up application security.
 *
 * This class configures various security-related beans and settings for the application,
 * including HTTP security rules, user details service, and password encoding.
 */
@Configuration
public class SecurityConfiguration {

    private final String rememberMeKey;

    public SecurityConfiguration(@Value("${finebeard.remember.me.key}") String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, OAuthSuccessHandler oAuthSuccessHandler, OAuthFailureHandler OAuthFailureHandler) throws Exception {
        return httpSecurity.
                authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/", "/users/login", "/users/login-error", "/users/register", "/users/activate/**", "/users/email-required", "/users/existing-user").permitAll()
                        .requestMatchers("/shop/**").permitAll()
                        .requestMatchers("/blog/**").permitAll()
                        .requestMatchers("/about", "/blog", "/help").permitAll()
                        .requestMatchers("/api/currency/convert/**").permitAll()
                        .requestMatchers("/shop/**").permitAll()
                        .anyRequest().authenticated()

        ).formLogin(
                formLogin -> {
                    formLogin
                            .loginPage("/users/login")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/")
                            .failureForwardUrl("/users/login-error");
//                            .failureHandler((request, response, exception) -> {
//                                response.setStatus(401);
//                            });
                }
        ).logout(
                logout -> {
                    logout
                            .logoutUrl("/users/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                }
        ).rememberMe(
                rememberMe ->
                        rememberMe
                                .key(rememberMeKey)
                                .rememberMeParameter("rememberme")
                                .rememberMeCookieName("rememberme")
        ).oauth2Login(
                oauth2 -> oauth2
                        .successHandler(oAuthSuccessHandler)
                        .failureHandler(OAuthFailureHandler)
        ).build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new FinebeardUserDetailsService(userRepository);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }


}
