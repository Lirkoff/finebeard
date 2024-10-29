package bg.softuni.finebeard.web;


import bg.softuni.finebeard.model.dto.ConvertRequestDTO;
import bg.softuni.finebeard.model.dto.MoneyDTO;
import bg.softuni.finebeard.service.CurrencyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CurrencyRestController handles REST API requests related to currency conversions.
 * It leverages CurrencyService to perform the conversion operations.
 */
@RestController
public class CurrencyRestController {

    /**
     * Service used for performing currency conversion operations.
     * This service provides methods to convert amounts from one currency to another.
     *
     * Utilized by the CurrencyRestController to handle requests for currency conversions.
     */
    private final CurrencyService currencyService;

    /**
     * Constructs a CurrencyRestController with the specified CurrencyService.
     *
     * @param currencyService an instance of CurrencyService to be used for currency conversion operations
     */
    public CurrencyRestController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }


    /**
     * Handles GET requests to convert a specified amount from the input currency to the target currency.
     *
     * @param convertRequestDTO Object containing the target currency and the amount to be converted.
     * @return A MoneyDTO object containing the converted currency and amount.
     */
    @GetMapping("/api/currency/convert")
    public MoneyDTO convert(@Valid ConvertRequestDTO convertRequestDTO) {
        return currencyService.convert(convertRequestDTO);
    }
}



