package bg.softuni.finebeard.model.entity;

import bg.softuni.finebeard.model.enums.BrandEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "brands")
public class BrandEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private BrandEnum name;

    @NotEmpty(message = "Model should not be empty!")
    private String model;

    public BrandEnum getName() {
        return name;
    }

    public BrandEntity setName(BrandEnum name) {
        this.name = name;
        return this;
    }

    public String getModel() {
        return model;
    }

    public BrandEntity setModel(String model) {
        this.model = model;
        return this;
    }
}
