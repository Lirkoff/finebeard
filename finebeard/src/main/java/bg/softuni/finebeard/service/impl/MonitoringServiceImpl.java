package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.service.MonitoringService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class MonitoringServiceImpl implements MonitoringService {
    private final Logger LOGGER = LoggerFactory.getLogger(MonitoringServiceImpl.class);
    private final Counter productSearches;
    private final Counter activationAttempts;


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


    @Override
    public void logProductSearch() {
        LOGGER.info("Product search was performed!");
        productSearches.increment();
    }

    @Override
    public void logActivationAttempts() {
        activationAttempts.increment();
    }

}
