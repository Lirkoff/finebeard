package bg.softuni.finebeard.model.entity;

import bg.softuni.finebeard.model.enums.BrandEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "brands")
public class BrandEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private BrandEnum brand;

    @OneToOne
    private ModelEntity model;


    public BrandEnum getBrand() {
        return brand;
    }

    public BrandEntity setBrand(BrandEnum brand) {
        this.brand = brand;
        return this;
    }

    public ModelEntity getModel() {
        return model;
    }

    public BrandEntity setModel(ModelEntity model) {
        this.model = model;
        return this;
    }
}
