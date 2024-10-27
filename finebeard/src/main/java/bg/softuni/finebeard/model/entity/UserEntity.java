package bg.softuni.finebeard.model.entity;

import bg.softuni.finebeard.model.enums.AuthProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(length = 100, nullable = false, unique = true)
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Email(message = "Email should be valid")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRolesEntity> roles;

    @NotEmpty
    private String password;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotNull
    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    @NotNull
    private AuthProvider authProvider;

    @Column(name = "provider_id")
    private String providerId;

    public UserEntity setRoles(Set<UserRolesEntity> roles) {
        this.roles = roles;
        return this;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public UserEntity setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
        return this;
    }

    public String getProviderId() {
        return providerId;
    }

    public UserEntity setProviderId(String providerId) {
        this.providerId = providerId;
        return this;
    }



    public Set<UserRolesEntity> getRoles() {
        return this.roles;
    }

    public UserEntity setRoles(UserRolesEntity role) {
        this.roles = new HashSet<>();

        this.roles.add(role);

        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return this.password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public boolean isActive() {
        return this.active;
    }

    public UserEntity setActive(boolean active) {
        this.active = active;
        return this;
    }
}
