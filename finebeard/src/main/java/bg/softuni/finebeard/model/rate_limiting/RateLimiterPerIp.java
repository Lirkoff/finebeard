package bg.softuni.finebeard.model.rate_limiting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to enforce rate limiting based on the client's IP address.
 * Methods annotated with this annotation will be rate-limited to control
 * the number of requests per IP address in a specified time window.
 * The actual rate limiting behavior is implemented in an aspect that
 * intercepts method calls with this annotation.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiterPerIp {
    String name();
}
