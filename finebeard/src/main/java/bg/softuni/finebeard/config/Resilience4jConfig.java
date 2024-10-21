package bg.softuni.finebeard.config;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;



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
