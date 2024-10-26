package bg.softuni.finebeard.model.entity;

import bg.softuni.finebeard.model.enums.UserRoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class UserRolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRoleEnum role;


    public Long getId() {
        return this.id;
    }

    public UserRolesEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public UserRoleEnum getRole() {
        return this.role;
    }

    public UserRolesEntity setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }

}
