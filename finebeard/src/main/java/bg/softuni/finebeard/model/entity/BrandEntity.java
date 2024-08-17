package bg.softuni.finebeard.model.entity;

import bg.softuni.finebeard.model.enums.BrandEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
@Table(name = "brands")
public class BrandEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private BrandEnum brand;

    @NotNull
    @OneToMany
    private Set<ModelEntity> models;

    public Set<ModelEntity> getModels() {
        return models;
    }

    public BrandEntity setModels(Set<ModelEntity> models) {
        this.models = models;
        return this;
    }

    public BrandEnum getBrand() {
        return brand;
    }

    public BrandEntity setBrand(BrandEnum brand) {
        this.brand = brand;
        return this;
    }
}
