package bg.softuni.finebeard.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "models")
public class ModelEntity extends BaseEntity {

    @NotBlank
    private String name;


    public String getName() {
        return name;
    }

    public ModelEntity setName(String name) {
        this.name = name;
        return this;
    }


}
