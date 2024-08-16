package bg.softuni.finebeard.model.dto;


import jakarta.validation.constraints.NotNull;

public record UserRegistrationDTO(@NotNull String firstName,
                                  @NotNull String lastName,
                                  @NotNull String email,
                                  String password,
                                  String confirmPassword) {

    public static UserRegistrationDTO empty() {
        return new UserRegistrationDTO(null,null,null,null,null);
    }
}
