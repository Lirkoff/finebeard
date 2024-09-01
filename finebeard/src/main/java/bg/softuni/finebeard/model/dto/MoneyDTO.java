package bg.softuni.finebeard.model.dto;

import java.math.BigDecimal;

public record MoneyDTO(String currency, BigDecimal amount) {
}
