package bg.softuni.finebeard.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {
    @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;
    private String name;
    private String description;
    private double price;
    private String imageUrl;


    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public ProductEntity setPrice(double price) {
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
}
