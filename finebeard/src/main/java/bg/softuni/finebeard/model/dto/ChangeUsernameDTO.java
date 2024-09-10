package bg.softuni.finebeard.model.dto;

import bg.softuni.finebeard.model.validation.UniqueUserEmail;
import jakarta.validation.constraints.Email;

public record ChangeUsernameDTO(@UniqueUserEmail @Email(message = "Invalid email!") String newEmail) {

    public static ChangeUsernameDTO empty() {
        return new ChangeUsernameDTO(null);
    }


}
