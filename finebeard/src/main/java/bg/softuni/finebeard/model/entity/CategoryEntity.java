package bg.softuni.finebeard.model.entity;

import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity{

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductCategoryEnum name;

    private String imageUrl;


    public ProductCategoryEnum getName() {
        return name;
    }

    public CategoryEntity setName(ProductCategoryEnum name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CategoryEntity setImageUrl(String imageUrl) {
        this.imageUrl = "../images/categories/" +
                imageUrl +
                ".png";
        return this;
    }
}
