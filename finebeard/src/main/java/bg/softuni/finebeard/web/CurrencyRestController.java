package bg.softuni.finebeard.web;


import bg.softuni.finebeard.model.dto.ConvertRequestDTO;
import bg.softuni.finebeard.model.dto.MoneyDTO;
import bg.softuni.finebeard.service.CurrencyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyRestController {

    private final CurrencyService currencyService;

    public CurrencyRestController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/api/currency/convert")
    public MoneyDTO convert(@Valid ConvertRequestDTO convertRequestDTO) {
        return currencyService.convert(convertRequestDTO);
    }
}



