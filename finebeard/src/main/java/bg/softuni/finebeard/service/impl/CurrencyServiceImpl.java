package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.dto.ConvertRequestDTO;
import bg.softuni.finebeard.model.dto.ExchangeRatesDTO;
import bg.softuni.finebeard.model.dto.MoneyDTO;
import bg.softuni.finebeard.model.entity.ExchangeRateEntity;
import bg.softuni.finebeard.repository.ExchangeRateRepository;
import bg.softuni.finebeard.service.CurrencyService;
import bg.softuni.finebeard.service.exception.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;


/**
 * Implementation of the CurrencyService interface, providing methods for
 * handling exchange rates and converting currency amounts.
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {
    /**
     * The LOGGER is used for logging messages within the CurrencyServiceImpl class.
     * It is a static final instance of the Logger class, retrieved from LoggerFactory
     * with the CurrencyServiceImpl class as the source.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    /**
     * A repository interface for managing exchange rate entities. It provides CRUD operations and query methods for
     * retrieving and manipulating exchange rate data stored in the database. This repository is used to interact with
     * the persistent storage of exchange rate information.
     */
    private final ExchangeRateRepository exchangeRateRepository;

    /**
     * Constructs a new instance of CurrencyServiceImpl with the provided ExchangeRateRepository.
     *
     * @param exchangeRateRepository the repository responsible for accessing exchange rate data
     */
    public CurrencyServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    /**
     * Refreshes the exchange rates by updating the rates of specific currencies
     * from the provided ExchangeRatesDTO object. This method attempts to fetch
     * the exchange rates for "BGN" to "USD" and "BGN" to "EUR" and saves them
     * in the repository if available.
     *
     * @param exchangeRatesDTO the data transfer object containing the base currency
     *                         and the exchange rates to be updated
     */
    @Override
    public void refreshRates(ExchangeRatesDTO exchangeRatesDTO) {

        LOGGER.info("Exchange rates received {}", exchangeRatesDTO);

        BigDecimal BGN_TO_USD = getExchangeRate(exchangeRatesDTO, "BGN", "USD").orElse(null);
        BigDecimal BGN_TO_EUR = getExchangeRate(exchangeRatesDTO, "BGN", "EUR").orElse(null);

        if (BGN_TO_USD != null) {
            ExchangeRateEntity exchangeRateEntity =
                    new ExchangeRateEntity().setCurrency("USD").setRate(BGN_TO_USD);
            exchangeRateRepository.save(exchangeRateEntity);
        } else {
            LOGGER.error("Unable to get exchange rate for BGN TO USD");
        }

        if (BGN_TO_EUR != null) {
            ExchangeRateEntity exchangeRateEntity =
                    new ExchangeRateEntity().setCurrency("EUR").setRate(BGN_TO_EUR);
            exchangeRateRepository.save(exchangeRateEntity);
        } else {
            LOGGER.error("Unable to get exchange rate for BGN TO EUR");
        }

        LOGGER.info("Rates refreshed...");
    }

    /**
     * Converts the specified amount to the target currency using the stored exchange rates.
     *
     * @param convertRequestDTO Object containing the target currency and the amount to be converted.
     * @return A MoneyDTO object containing the converted currency and amount.
     */
    @Override
    public MoneyDTO convert(ConvertRequestDTO convertRequestDTO) {
        ExchangeRateEntity exchangeRateEntity = exchangeRateRepository
                .findById(convertRequestDTO.target())
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Conversion to target " +
                                convertRequestDTO.target() + "not possible!"));

        return new MoneyDTO(convertRequestDTO.target(),
                exchangeRateEntity.getRate().multiply(convertRequestDTO.amount()));
    }

    /**
     * Retrieves the exchange rate between two currencies based on the provided exchange rates data.
     * The method supports converting between the base currency and other currencies, as well as between two non-base currencies.
     *
     * @param exchangeRatesDTO Contains the base currency and a map of exchange rates.
     * @param from             The currency code to convert from.
     * @param to               The currency code to convert to.
     * @return An Optional containing the exchange rate if found, otherwise an empty Optional.
     */
    private static Optional<BigDecimal> getExchangeRate(
            ExchangeRatesDTO exchangeRatesDTO,
            String from,
            String to
    ) {
        Objects.requireNonNull(from, "From currency cannot be null!");
        Objects.requireNonNull(to, "To currency cannot be null!");


        //e.g. USD -> USD
        if (Objects.equals(from, to)) {
            return Optional.of(BigDecimal.ONE);
        }


        if (from.equals(exchangeRatesDTO.base())) {
            //e.g USD -> BGN
            if (exchangeRatesDTO.rates().containsKey(to)) {
                return Optional.of(exchangeRatesDTO.rates().get(to));
            }
        } else if (Objects.equals(to, exchangeRatesDTO.base())) {
            //e.g BGN -> USD
            if (exchangeRatesDTO.rates().containsKey(from)) {
                return Optional.of(BigDecimal.ONE.divide(
                        exchangeRatesDTO.rates().get(from),
                        3,
                        RoundingMode.DOWN
                ));
            }
        } else if (exchangeRatesDTO.rates().containsKey(from) &&
                exchangeRatesDTO.rates().containsKey(to)) {
            return Optional.of(
                    exchangeRatesDTO.rates().get(to)
                            .divide(exchangeRatesDTO.rates().get(from),
                                    3, RoundingMode.DOWN)

            );
        }

        return Optional.empty();
    }

}
