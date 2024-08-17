package bg.softuni.finebeard.model.entity;

import bg.softuni.finebeard.model.enums.BrandEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "brands")
public class BrandEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private BrandEnum brand;

    public BrandEnum getBrand() {
        return brand;
    }

    public BrandEntity setBrand(BrandEnum brand) {
        this.brand = brand;
        return this;
    }
}
