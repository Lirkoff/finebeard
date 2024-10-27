package bg.softuni.finebeard.service.aop;

import bg.softuni.finebeard.service.MonitoringService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * MonitoringAspect is an aspect that intercepts specified pointcuts related to
 * product searches and rate-limited activation attempts to log these events using
 * the MonitoringService.
 */
@Aspect
@Component
public class MonitoringAspect {
    /**
     * An instance of MonitoringService used to log product search
     * events and rate-limited activation attempts. This service
     * facilitates tracking and monitoring these specific events
     * within the system for analytical and monitoring purposes.
     */
    private final MonitoringService monitoringService;

    /**
     * Constructor for MonitoringAspect that initializes the aspect with a MonitoringService.
     *
     * @param monitoringService the service used to log product searches and activation attempts.
     */
    public MonitoringAspect(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    /**
     * Method to log a product search event. It is intended to be executed before
     * the pointcut defined in trackProductSearch(). The associated monitoringService
     * is used to log the search event for tracking and analytical purposes.
     */
    @Before("PointCuts.trackProductSearch()")
    public void logOfferSearch() {
        monitoringService.logProductSearch();
    }

    /**
     * Logs the execution of rate-limited activation attempts.
     * This method is triggered after the pointcut defined in
     * PointCuts.trackRateLimitedActivationAttempts(), and it
     * delegates the logging to the monitoringService.
     */
    @After("PointCuts.trackRateLimitedActivationAttempts()")
    public void logRateLimitedActivationAttempts() {
        monitoringService.logActivationAttempts();
    }

}
