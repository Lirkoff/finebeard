package bg.softuni.finebeard.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AddProductDTO(
        @NotEmpty
        @Size(min = 5, max = 512)
        String description,
        String imageUrl

) {

}
