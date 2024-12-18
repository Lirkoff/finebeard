package bg.softuni.finebeard.init;

import bg.softuni.finebeard.config.OpenExchangeRateConfig;
import bg.softuni.finebeard.model.dto.ExchangeRatesDTO;
import bg.softuni.finebeard.service.CurrencyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * RatesInit is a Spring Component that initializes the exchange rates
 * when the application starts. It implements the CommandLineRunner
 * interface to execute code after the application context is loaded.
 *
 * This class uses a RestTemplate to fetch the latest exchange rates from
 * the OpenExchangeRate API and refreshes the rates in the application
 * using the CurrencyService.
 */
@Component
public class RatesInit implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final OpenExchangeRateConfig openExchangeRateConfig;
    private final CurrencyService currencyService;

    public RatesInit(RestTemplate restTemplate, OpenExchangeRateConfig openExchangeRateConfig, CurrencyService currencyService) {
        this.restTemplate = restTemplate;
        this.openExchangeRateConfig = openExchangeRateConfig;
        this.currencyService = currencyService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (openExchangeRateConfig.isEnabled()) {
            String openExchangeRateUrlTemplate =
                    openExchangeRateConfig.getSchema() +
                            "://" +
                            openExchangeRateConfig.getHost() +
                            openExchangeRateConfig.getPath() +
                            "?app_id={app_id}&symbols={symbols}";
            Map<String, String> requestParams = Map.of(
                    "app_id", openExchangeRateConfig.getAppId(),
                    "symbols", String.join(",", openExchangeRateConfig.getSymbols())
            );

            ExchangeRatesDTO exchangeRatesDTO = restTemplate
                    .getForObject(openExchangeRateUrlTemplate,
                            ExchangeRatesDTO.class,
                            requestParams
                    );

            currencyService.refreshRates(exchangeRatesDTO);
        }
    }
}
