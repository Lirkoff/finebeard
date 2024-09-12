package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.service.MonitoringService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class MonitoringServiceImpl implements MonitoringService {
    private Logger LOGGER = LoggerFactory.getLogger(MonitoringServiceImpl.class);

    private Counter productSearches;

    public MonitoringServiceImpl(MeterRegistry meterRegistry) {
        productSearches = Counter
                .builder("product_search_cnt")
                .description("How many product searches we have performed")
                .register(meterRegistry);
    }


    @Override
    public void logProductSearch() {
        LOGGER.info("Product search was performed!");
        productSearches.increment();
    }
}
