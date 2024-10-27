package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.ConvertRequestDTO;
import bg.softuni.finebeard.model.dto.ExchangeRatesDTO;
import bg.softuni.finebeard.model.dto.MoneyDTO;

/**
 * The CurrencyService interface provides methods for handling exchange rates and converting currency amounts.
 */
public interface CurrencyService {
    /**
     * Refreshes the exchange rates using the provided ExchangeRatesDTO. The rates are updated based on the base currency and
     * the map of currency rates contained within the ExchangeRatesDTO object.
     *
     * @param exchangeRatesDTO the data transfer object containing the base currency and the exchange rates to be updated
     */
    void refreshRates(ExchangeRatesDTO exchangeRatesDTO);

    /**
     * Converts the amount specified in the ConvertRequestDTO to the target currency.
     *
     * @param convertRequestDTO Object containing the target currency and the amount to be converted.
     * @return A MoneyDTO object containing the converted currency and amount.
     */
    MoneyDTO convert(ConvertRequestDTO convertRequestDTO);
}
