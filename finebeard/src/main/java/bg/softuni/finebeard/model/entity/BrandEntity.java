package bg.softuni.finebeard.model.entity;

import bg.softuni.finebeard.model.enums.BrandEnum;
import jakarta.persistence.*;


@Entity
@Table(name = "brands")
public class BrandEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private BrandEnum name;


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
