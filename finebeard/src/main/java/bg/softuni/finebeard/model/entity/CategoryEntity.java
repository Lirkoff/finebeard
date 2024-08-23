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


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "categories_brands",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id"))
    private List<BrandEntity> brands;

    public ProductCategoryEnum getName() {
        return name;
    }

    public CategoryEntity setName(ProductCategoryEnum name) {
        this.name = name;
        return this;
    }

    public List<BrandEntity> getBrands() {
        return brands;
    }

    public CategoryEntity setBrands(List<BrandEntity> brands) {
        this.brands = brands;
        return this;
    }
}
