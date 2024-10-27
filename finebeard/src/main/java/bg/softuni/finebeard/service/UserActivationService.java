package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.events.UserRegisteredEvent;

/**
 * Interface for handling user activation processes.
 */
public interface UserActivationService {

    /**
     * Handles the event of user registration.
     *
     * @param event The event triggered upon user registration, containing user details.
     */
    void userRegistered(UserRegisteredEvent event);

    /**
     * Cleans up obsolete activation entities from the database.
     * This includes expired or invalid activation codes that are no longer needed.
     *
     * @return The number of obsolete activation entities that were deleted.
     */
    Long cleanUpObsoleteActivationEntities();

    /**
     * Creates a new activation code for the given user email.
     *
     * @param userEmail the email of the user for whom the activation code is being created
     * @return the generated activation code
     */
    String createActivationCode(String userEmail);

    /**
     * Activates a user account based on the provided activation code.
     *
     * @param activationCode the activation code used to activate the user account
     * @param ipAddress the IP address from which the activation request originated
     * @return true if the user account was successfully activated, false otherwise
     */
    boolean activateUser(String activationCode, String ipAddress);

    /**
     * Cleans up obsolete user activation attempts from the database.
     *
     * @return The number of activation attempts that were deleted.
     */
    Long cleanUpObsoleteActivationAttempts();
}
