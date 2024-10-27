package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.config.ReCaptchaConfig;
import bg.softuni.finebeard.model.dto.ReCaptchaResponseDTO;
import bg.softuni.finebeard.service.ReCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Optional;


/**
 * Implementation of the ReCaptchaService interface for verifying reCAPTCHA tokens.
 */
@Service
public class ReCaptchaServiceImpl implements ReCaptchaService {
    /**
     * Logger for logging events and errors in the ReCaptchaServiceImpl class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReCaptchaServiceImpl.class);

    /**
     * WebClient instance used to perform HTTP requests for verifying reCAPTCHA tokens.
     */
    private final WebClient webClient;

    /**
     * Configuration settings for the reCAPTCHA service, containing site key and secret key.
     */
    private final ReCaptchaConfig reCaptchaConfig;


    /**
     * Constructs a new ReCaptchaServiceImpl with the specified WebClient and ReCaptchaConfig.
     *
     * @param webClient       the WebClient to be used for making HTTP requests
     * @param reCaptchaConfig the ReCaptchaConfig containing configuration properties for reCAPTCHA
     */
    public ReCaptchaServiceImpl(WebClient webClient, ReCaptchaConfig reCaptchaConfig) {
        this.webClient = webClient;
        this.reCaptchaConfig = reCaptchaConfig;
    }


    /**
     * Verifies the provided reCAPTCHA token and returns the response.
     *
     * @param token the reCAPTCHA token to be verified
     * @return an Optional containing the response of the reCAPTCHA verification,
     * or an empty Optional if verification fails or an error occurs
     */
    @Override
    public Optional<ReCaptchaResponseDTO> verify(String token) {
        ReCaptchaResponseDTO response = webClient
                .post()
                .uri(this::buildRecaptchaURI)
                .body(BodyInserters
                        .fromFormData("secret", reCaptchaConfig.getSecret())
                        .with("response", token))
                .retrieve()
                .bodyToMono(ReCaptchaResponseDTO.class)
                .doOnError(t -> LOGGER.error("Error fetching google response...", t))
                .onErrorComplete()
                .block();

        return Optional.ofNullable(response);
    }

    /**
     * Builds the URI for the reCAPTCHA verification endpoint.
     *
     * @param uriBuilder the UriBuilder instance used to construct the URI
     * @return the URI for the reCAPTCHA verification endpoint
     */
    private URI buildRecaptchaURI(UriBuilder uriBuilder) {

        //REST endpoint for google verification.
        //https://www.google.com/recaptcha/api/siteverify

        return uriBuilder
                .scheme("https")
                .host("www.google.com")
                .path("/recaptcha/api/siteverify")
                .build();
    }
}
