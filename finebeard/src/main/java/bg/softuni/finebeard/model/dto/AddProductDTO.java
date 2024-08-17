package bg.softuni.finebeard.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AddProductDTO(@NotEmpty(message = "Description should not be empty!")
                            @Size(min = 5, max = 500, message = "Size must be between 5 and 500 symbols!")
                            String description,
                            @Positive @NotNull Long brandId,
                            @Positive @NotNull Long categoryId,
                            @NotEmpty(message = "Enter image URL!") String imageUrl,
                            @Positive(message = "Price should be a positive number!") @NotNull(message = "Enter price!") Integer price) {

        public static AddProductDTO empty() {
                return new AddProductDTO( null, null, null, null, null);
        }
}
