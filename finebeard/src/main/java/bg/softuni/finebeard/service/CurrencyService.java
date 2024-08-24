package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.ExchangeRatesDTO;

public interface CurrencyService {
    void refreshRates(ExchangeRatesDTO exchangeRatesDTO);
}
