package bg.softuni.finebeard.web;


import bg.softuni.finebeard.model.dto.ConvertRequestDTO;
import bg.softuni.finebeard.model.dto.MoneyDTO;
import bg.softuni.finebeard.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
     * Converts BGN to a target currency.
     *
     * @param convertRequestDTO Object containing the target currency and the amount to be converted.
     * @return A MoneyDTO object containing the converted currency and amount.
     */
    @Operation(summary = "Converts BGN to a target currency.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returned when we successfully converted the currency.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MoneyDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "There is no information about this currency",
                    content = @Content
            )
    })
    @Parameter(name = "target", description = "The target currency", required = true)
    @Parameter(name = "amount", description = "The amount to be converted", required = true)
    @GetMapping("/api/currency/convert")
    public MoneyDTO convert(@Valid ConvertRequestDTO convertRequestDTO) {
        return currencyService.convert(convertRequestDTO);
    }
}



