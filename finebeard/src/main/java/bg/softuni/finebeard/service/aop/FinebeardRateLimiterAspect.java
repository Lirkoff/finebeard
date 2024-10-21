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



@Aspect
@Component
public class FinebeardRateLimiterAspect {
    private final Logger LOGGER = LoggerFactory.getLogger(FinebeardRateLimiterAspect.class);
    private final HttpServletRequest request;
    private final RateLimiterRegistry rateLimiterRegistry;
    private final MonitoringService monitoringService;


    public FinebeardRateLimiterAspect(HttpServletRequest request, RateLimiterRegistry rateLimiterRegistry, MonitoringService monitoringService) {
        this.request = request;
        this.rateLimiterRegistry = rateLimiterRegistry;
        this.monitoringService = monitoringService;
    }

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

    private String getClientIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
