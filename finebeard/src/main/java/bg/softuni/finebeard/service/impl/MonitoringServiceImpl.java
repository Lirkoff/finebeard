package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.service.MonitoringService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Implementation of the MonitoringService interface that logs and tracks various system events,
 * particularly related to product searches and activation attempts.
 */
@Service
public class MonitoringServiceImpl implements MonitoringService {
    /**
     * Logger instance used for logging events related to the MonitoringServiceImpl class.
     * Primarily, it logs product search operations and activation attempts.
     */
    private final Logger LOGGER = LoggerFactory.getLogger(MonitoringServiceImpl.class);

    /**
     * A counter to track the number of product searches performed.
     * This counter is used to monitor and log product search activities within the system.
     */
    private final Counter productSearches;

    /**
     * Counter that tracks the number of rate-limiting activation attempts performed.
     * This counter is used for monitoring and analytical purposes to understand
     * the frequency and distribution of activation attempts in the system.
     */
    private final Counter activationAttempts;

    /**
     * Constructs a new MonitoringServiceImpl with the specified MeterRegistry for
     * tracking product searches and activation attempts.
     *
     * @param meterRegistry the MeterRegistry instance used to register counters
     *                      for product searches and activation attempts
     */
    public MonitoringServiceImpl(MeterRegistry meterRegistry) {
        productSearches = Counter
                .builder("product_search_cnt")
                .description("How many product searches we have performed")
                .register(meterRegistry);

        activationAttempts= Counter
                .builder("activation_attempts_cnt")
                .description("How many rate-limiting activation attempts were performed")
                .register(meterRegistry);
    }

    /**
     * Logs an event indicating that a product search has been performed.
     * This method increases the product search counter and records the event in the system logs.
     */
    @Override
    public void logProductSearch() {
        LOGGER.info("Product search was performed!");
        productSearches.increment();
    }

    /**
     * Logs the attempts made to activate a product or service within the system.
     * This method increments the counter that tracks the number of rate-limited activation attempts,
     * allowing the system to monitor and analyze these occurrences.
     */
    @Override
    public void logActivationAttempts() {
        activationAttempts.increment();
    }

}
