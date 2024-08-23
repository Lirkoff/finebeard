package bg.softuni.finebeard.model.dto;

import java.math.BigDecimal;

public record ProductDetailDTO(
        String id,
        String description,
        String brand,
        String model,
        String imageUrl,
        BigDecimal price
) {

    public String summary() {
        return brand + " " + model + " " + price;
    }
}
