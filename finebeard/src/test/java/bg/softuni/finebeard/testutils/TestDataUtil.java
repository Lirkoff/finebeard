package bg.softuni.finebeard.testutils;

import bg.softuni.finebeard.model.entity.ExchangeRateEntity;
import bg.softuni.finebeard.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TestDataUtil {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public void createExchangeRate(String currency, BigDecimal rate) {
        exchangeRateRepository.save(
                new ExchangeRateEntity()
                        .setCurrency(currency)
                        .setRate(rate)
        );
    }

    public void cleanAllTestData() {
        exchangeRateRepository.deleteAll();
    }
}
