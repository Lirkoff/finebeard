package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.UserEmailRolesDTO;
import bg.softuni.finebeard.model.dto.UserRegistrationDTO;
import bg.softuni.finebeard.model.enums.AuthProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

/**
 * UserService interface defines methods for user management and authentication.
 */
public interface UserService {

    /**
     * Registers a new user in the system using the provided UserRegistrationDTO object.
     *
     * @param userRegistrationDTO the DTO containing user registration details
     */
    void registerUser(UserRegistrationDTO userRegistrationDTO);

    /**
     * Adds a user role based on the provided email.
     *
     * @param email The email of the user to whom the role will be added.
     */
    void addUserRole(String email);

    /**
     * Removes a user's role based on their email address.
     *
     * @param email the email address of the user whose role is to be removed
     */
    void removeUserRole(String email);

    /**
     * Retrieves a paginated list of all user email addresses and roles.
     *
     * @param pageable the pagination information
     * @return a paginated list of user email addresses and roles
     */
    Page<UserEmailRolesDTO> getAllUsersNamesAndRoles(Pageable pageable);

    /**
     * Creates a user if one does not already exist based on the provided email and provider ID.
     *
     * @param email the user's email address
     * @param names the user's full name
     * @param providerId the unique identifier for the user from the authentication provider
     * @param authProvider the authentication provider (e.g., LOCAL, GITHUB, GOOGLE, FACEBOOK)
     */
    void createUserIfDoesNotExist(String email, String names, String providerId, AuthProvider authProvider);

    /**
     * Authenticates a user based on their email.
     *
     * @param email The email of the user attempting to log in
     * @return The authentication object containing authentication details of the logged-in user
     */
    Authentication login(String email);

    /**
     * Changes the username of a user.
     *
     * @param currentUserName The current username of the user.
     * @param newUserName The new username to be set for the user.
     */
    void changeUsername(String currentUserName, String newUserName);


}
