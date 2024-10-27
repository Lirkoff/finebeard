package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.ReCaptchaResponseDTO;

import java.util.Optional;

/**
 * Service interface for verifying reCAPTCHA tokens.
 */
public interface ReCaptchaService {

    /**
     * Verifies the provided reCAPTCHA token and returns the response.
     *
     * @param token the reCAPTCHA token to be verified
     * @return an Optional containing the response of the reCAPTCHA verification,
     *         or an empty Optional if verification fails or an error occurs
     */
    Optional<ReCaptchaResponseDTO> verify(String token);
}
