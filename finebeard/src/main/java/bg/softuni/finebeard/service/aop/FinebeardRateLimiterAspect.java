package bg.softuni.finebeard.service.aop;

import bg.softuni.finebeard.model.rate_limiting.RateLimiterPerIp;
import bg.softuni.finebeard.service.MonitoringService;
import bg.softuni.finebeard.service.exception.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.RateLimiter;

import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 * FinebeardRateLimiterAspect class is an aspect for applying rate limiting on methods
 * annotated with {@link RateLimiterPerIp}. It leverages the RateLimiterRegistry to enforce
 * rate limits on a per-IP basis.
 *
 * The class logs an activation attempt and throws a {@link RequestNotPermitted} exception when the rate limit is exceeded.
 */
@Aspect
@Component
public class FinebeardRateLimiterAspect {
    /**
     * Logger instance for logging information and warnings within the FinebeardRateLimiterAspect class.
     * Utilizes the SLF4J framework for standardized logging across different implementations.
     */
    private final Logger LOGGER = LoggerFactory.getLogger(FinebeardRateLimiterAspect.class);

    /**
     * Represents the HTTP request being processed by the FinebeardRateLimiterAspect.
     * It is used to extract client-specific information such as the IP address
     * to apply rate limiting on a per-IP basis.
     */
    private final HttpServletRequest request;

    /**
     * A registry that provides {@link RateLimiter} instances based on their names, allowing
     * for the management and configuration of rate limiters within the system. This registry
     * is used by the {@link FinebeardRateLimiterAspect} to apply rate limiting on a per-IP basis
     * for methods annotated with {@link RateLimiterPerIp}.
     */
    private final RateLimiterRegistry rateLimiterRegistry;

    /**
     * An instance of the {@link MonitoringService} interface that is used to log various events
     * within the system, particularly those related to rate-limited activation attempts.
     * This service helps track and monitor occurrences such as rate limit exceedances
     * for analytical and monitoring purposes.
     */
    private final MonitoringService monitoringService;

    /**
     * Constructs a FinebeardRateLimiterAspect object which handles rate limiting
     * based on the client IP address.
     *
     * @param request The HttpServletRequest instance to obtain client IP addresses.
     * @param rateLimiterRegistry The registry for managing RateLimiter instances.
     * @param monitoringService The service for logging monitoring events.
     */
    public FinebeardRateLimiterAspect(HttpServletRequest request, RateLimiterRegistry rateLimiterRegistry, MonitoringService monitoringService) {
        this.request = request;
        this.rateLimiterRegistry = rateLimiterRegistry;
        this.monitoringService = monitoringService;
    }

    /**
     * Applies rate limiting around methods annotated with {@link RateLimiterPerIp}.
     * This method extracts the client's IP address and uses it to create a unique
     * rate limiter. If the rate limit is not exceeded, the method proceeds; otherwise,
     * it logs the attempt and throws a {@link RequestNotPermitted} exception.
     *
     * @param joinPoint the join point representing the method execution
     * @param rateLimiter the annotation providing rate limiting configuration
     * @return the result of the join point's method execution if permitted
     * @throws Throwable any exception thrown by the join point's method or rate limiting process
     */
    @Around("@annotation(rateLimiter)")
    public Object rateLimiterAround(ProceedingJoinPoint joinPoint, RateLimiterPerIp rateLimiter) throws Throwable {
        String ipAddress = getClientIp(request);
        String rateLimiterName = rateLimiter.name() + "_" + ipAddress;


        RateLimiter limiter = rateLimiterRegistry.rateLimiter(rateLimiterName);


        if (limiter.acquirePermission()) {
            return joinPoint.proceed();
        } else {
            limiter.getEventPublisher()
                    .onFailure(event -> LOGGER.warn("Activation attempt rate-limited for IP: {}", ipAddress));
            monitoringService.logActivationAttempts();
            throw new RequestNotPermitted("Rate limit exceeded for IP: " + ipAddress);
        }
    }

    /**
     * Retrieves the client's IP address from the given HttpServletRequest.
     *
     * @param request the HttpServletRequest object containing the client's request
     * @return the client's IP address as a String
     */
    private String getClientIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
