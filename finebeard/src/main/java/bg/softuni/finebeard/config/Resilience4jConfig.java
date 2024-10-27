package bg.softuni.finebeard.config;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;



/**
 * Configuration class for defining Resilience4j rate limiter settings.
 * This class binds to properties with the prefix 'resilience4j.ratelimiter.instances.activate-user'.
 * It defines the limit for the rate limiter, the refresh period for the rate limits, and the timeout duration.
 *
 * The properties are:
 *   - limitForPeriod: The number of permits available during one limit refresh period.
 *   - limitRefreshPeriod: The time duration for which the limit of permits will be refreshed.
 *   - timeoutDuration: The maximum wait time duration to acquire a permission.
 *
 * This class also provides a Bean for the RateLimiterRegistry which uses these configurations.
 *
 * The available methods are:
 * - getLimitForPeriod: Returns the current setting for the limitForPeriod.
 * - setLimitForPeriod: Sets the limitForPeriod.
 * - getLimitRefreshPeriod: Returns the current setting for the limitRefreshPeriod.
 * - setLimitRefreshPeriod: Sets the limitRefreshPeriod.
 * - getTimeoutDuration: Returns the current setting for the timeoutDuration.
 * - setTimeoutDuration: Sets the timeoutDuration.
 * - rateLimiterRegistry: Provides a RateLimiterRegistry Bean configured with the defined settings.
 */
@Configuration
@ConfigurationProperties(prefix = "resilience4j.ratelimiter.instances.activate-user")
public class Resilience4jConfig {
    private int limitForPeriod;
    private Duration limitRefreshPeriod;
    private Duration timeoutDuration;


    public int getLimitForPeriod() {
        return limitForPeriod;
    }

    public void setLimitForPeriod(int limitForPeriod) {
        this.limitForPeriod = limitForPeriod;
    }

    public Duration getLimitRefreshPeriod() {
        return limitRefreshPeriod;
    }

    public void setLimitRefreshPeriod(Duration limitRefreshPeriod) {
        this.limitRefreshPeriod = limitRefreshPeriod;
    }

    public Duration getTimeoutDuration() {
        return timeoutDuration;
    }

    public void setTimeoutDuration(Duration timeoutDuration) {
        this.timeoutDuration = timeoutDuration;
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        return RateLimiterRegistry.of(
                RateLimiterConfig.custom()
                        .limitForPeriod(limitForPeriod)
                        .limitRefreshPeriod(limitRefreshPeriod)
                        .timeoutDuration(timeoutDuration)
                        .build());
    }



    }
