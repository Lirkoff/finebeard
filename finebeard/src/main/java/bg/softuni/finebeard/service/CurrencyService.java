package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.ConvertRequestDTO;
import bg.softuni.finebeard.model.dto.ExchangeRatesDTO;
import bg.softuni.finebeard.model.dto.MoneyDTO;

public interface CurrencyService {
    void refreshRates(ExchangeRatesDTO exchangeRatesDTO);
    MoneyDTO convert(ConvertRequestDTO convertRequestDTO);
}
