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


@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    private final ExchangeRateRepository exchangeRateRepository;

    public CurrencyServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

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
