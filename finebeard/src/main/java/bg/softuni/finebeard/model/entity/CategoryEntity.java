package bg.softuni.finebeard.model.entity;

import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity{


    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductCategoryEnum category;

    public ProductCategoryEnum getCategory() {
        return category;
    }

    public CategoryEntity setCategory(ProductCategoryEnum category) {
        this.category = category;
        return this;
    }
}
