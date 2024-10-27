package bg.softuni.finebeard.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for Google reCAPTCHA.
 *
 * This class is used for binding the properties defined in the application's
 * configuration file to Java object fields. It holds the site key and secret key
 * required for reCAPTCHA verification.
 */
@Configuration
@ConfigurationProperties(prefix = "google.recaptcha")
public class ReCaptchaConfig {

    private String site;
    private String secret;


    public String getSite() {
        return site;
    }

    public ReCaptchaConfig setSite(String site) {
        this.site = site;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public ReCaptchaConfig setSecret(String secret) {
        this.secret = secret;
        return this;
    }
}
