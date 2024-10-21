package bg.softuni.finebeard.service.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    @Pointcut("execution(* bg.softuni.finebeard.service.ShopService.getAllProducts(..))")
    public void trackProductSearch(){};

    @Pointcut("execution(* bg.softuni.finebeard.service.aop.FinebeardRateLimiterAspect.rateLimiterAround(..))")
    public void trackRateLimitedActivationAttempts(){};

}
