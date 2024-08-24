package bg.softuni.finebeard.model.entity;

import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity{

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductCategoryEnum name;


    public ProductCategoryEnum getName() {
        return name;
    }

    public CategoryEntity setName(ProductCategoryEnum name) {
        this.name = name;
        return this;
    }


}
