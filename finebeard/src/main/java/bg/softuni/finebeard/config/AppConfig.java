package bg.softuni.finebeard.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


/**
 * Configuration class for setting up application-wide bean definitions.
 *
 * This class is marked with the @Configuration annotation, indicating that it has
 * one or more @Bean methods that will be processed by the Spring container during
 * the application startup.
 */
@Configuration
public class AppConfig {
    /**
     * Creates a {@link WebClient} bean configured to use application/x-www-form-urlencoded
     * content type for POST requests.
     *
     * @return a {@link WebClient} instance with the default header set to
     *         application/x-www-form-urlencoded
     */
    @Bean
    public WebClient formPostWebClient() {
        return WebClient
                .builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }

    /**
     * Creates and configures a {@link RestTemplate} bean with a default Content-Type header set to application/json.
     *
     * @param restTemplateBuilder the {@link RestTemplateBuilder} used to configure the {@link RestTemplate}.
     * @return a configured {@link RestTemplate} instance.
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }


}
