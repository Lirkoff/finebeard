package bg.softuni.finebeard.model.entity;

import bg.softuni.finebeard.model.enums.BrandEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "brands")
public class BrandEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private BrandEnum name;

    @OneToOne
    private ModelEntity model;

    @ManyToOne
    private CategoryEntity category;


    public BrandEnum getName() {
        return name;
    }

    public BrandEntity setName(BrandEnum name) {
        this.name = name;
        return this;
    }

    public ModelEntity getModel() {
        return model;
    }

    public BrandEntity setModel(ModelEntity model) {
        this.model = model;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public BrandEntity setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }
}
