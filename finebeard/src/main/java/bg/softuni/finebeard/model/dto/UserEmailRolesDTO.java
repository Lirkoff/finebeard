package bg.softuni.finebeard.model.dto;

public class UserEmailRolesDTO {

    private String email;
    private String roles;

    public UserEmailRolesDTO(String email, String roles) {
        this.email = email;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public UserEmailRolesDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRoles() {
        return roles;
    }

    public UserEmailRolesDTO setRoles(String roles) {
        this.roles = roles;
        return this;
    }
}
