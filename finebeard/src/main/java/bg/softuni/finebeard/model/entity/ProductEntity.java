package bg.softuni.finebeard.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {
    @JdbcTypeCode(Types.VARCHAR)
    @NotNull(message = "UUID cannot be null")
    private UUID uuid;

    @NotNull(message = "Category cannot be null")
    @ManyToOne
    private CategoryEntity category;

    @NotNull(message = "Brand cannot be null")
    @ManyToOne
    private BrandEntity brand;


    private String model;


    @NotEmpty(message = "Description cannot be empty")
//    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price should be a positive value!")
    private BigDecimal price;

    @NotEmpty(message = "Image URL cannot be empty")
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
        this.imageUrl = imageUrl;
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

    public String getModel() {
        return model;
    }

    public ProductEntity setModel(String model) {
        this.model = model;
        return this;
    }
}
