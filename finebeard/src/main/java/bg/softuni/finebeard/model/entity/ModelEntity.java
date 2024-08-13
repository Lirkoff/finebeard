package bg.softuni.finebeard.model.entity;

import bg.softuni.finebeard.model.enums.ModelCategory;
import jakarta.persistence.*;

@Entity
@Table(name = "models")
public class ModelEntity extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private ModelCategory modelCategory;

    @ManyToOne
    private CategoryEntity category;


    public String getName() {
        return name;
    }

    public ModelEntity setName(String name) {
        this.name = name;
        return this;
    }


}
