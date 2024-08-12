package bg.softuni.finebeard.model.entity;

import bg.softuni.finebeard.model.enums.ModelCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "models")
public class ModelEntity extends BaseEntity {

    private String name;


    @Enumerated
    private ModelCategory modelCategory;

    public String getName() {
        return name;
    }

    public ModelEntity setName(String name) {
        this.name = name;
        return this;
    }

    public ModelCategory getModelCategory() {
        return modelCategory;
    }

    public ModelEntity setModelCategory(ModelCategory modelCategory) {
        this.modelCategory = modelCategory;
        return this;
    }
}
