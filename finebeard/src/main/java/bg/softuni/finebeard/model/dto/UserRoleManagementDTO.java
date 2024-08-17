package bg.softuni.finebeard.model.dto;

import jakarta.validation.constraints.Email;

public record UserRoleManagementDTO(@Email String username) {
}
