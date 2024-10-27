package bg.softuni.finebeard.service.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * The PointCuts class defines pointcut expressions that match join points
 * in the application's code. These pointcuts are used to specify where
 * advice should be applied within the application's methods.
 */
public class PointCuts {

    /**
     * Pointcut that matches the execution of the getAllProducts method
     * in the ShopService class. This is used to track product search
     * operations within the application.
     */
    @Pointcut("execution(* bg.softuni.finebeard.service.ShopService.getAllProducts(..))")
    public void trackProductSearch(){};

    /**
     * Pointcut that matches the execution of the rateLimiterAround method
     * within the FinebeardRateLimiterAspect aspect. This pointcut is intended
     * to track the activation attempts that are subject to rate limiting.
     */
    @Pointcut("execution(* bg.softuni.finebeard.service.aop.FinebeardRateLimiterAspect.rateLimiterAround(..))")
    public void trackRateLimitedActivationAttempts(){};

}
