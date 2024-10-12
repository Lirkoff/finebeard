package bg.softuni.finebeard.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {
    @JdbcTypeCode(Types.VARCHAR)
    @NotNull
    private UUID uuid;

    @NotNull
    @ManyToOne
    private CategoryEntity category;

    @NotNull
    @ManyToOne
    private BrandEntity brand;


    @NotEmpty
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    private BigDecimal price;

    @NotEmpty
    private String imageUrl;


    public BrandEntity getBrand() {
        return this.brand;
    }

    public ProductEntity setBrand(BrandEntity brand) {
        this.brand = brand;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductEntity setImageUrl(String imageUrl) {
        this.imageUrl = "../images/categories/" +
                imageUrl +
                ".png";
        return this;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ProductEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public ProductEntity setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }


}
