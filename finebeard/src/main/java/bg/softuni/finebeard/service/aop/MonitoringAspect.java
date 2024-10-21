package bg.softuni.finebeard.service.aop;

import bg.softuni.finebeard.service.MonitoringService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MonitoringAspect {

    private final MonitoringService monitoringService;

    public MonitoringAspect(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @Before("PointCuts.trackProductSearch()")
    public void logOfferSearch() {
        monitoringService.logProductSearch();
    }

    @After("PointCuts.trackRateLimitedActivationAttempts()")
    public void logRateLimitedActivationAttempts() {
        monitoringService.logActivationAttempts();
    }

}
