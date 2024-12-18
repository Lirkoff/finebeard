package bg.softuni.finebeard.model.dto;


import bg.softuni.finebeard.model.validation.FieldMatch;
import bg.softuni.finebeard.model.validation.UniqueUserEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Passwords must be identical!"
)
public record UserRegistrationDTO(@NotEmpty String firstName,
                                  @NotEmpty String lastName,
                                  @NotEmpty @Email @UniqueUserEmail String email,
                                  @NotEmpty String password,
                                  @NotEmpty String confirmPassword) {

    public static UserRegistrationDTO empty() {
        return new UserRegistrationDTO(null,null,null,null,null);
    }

    public String fullName() {
        return firstName + " " + lastName;
    }
}
