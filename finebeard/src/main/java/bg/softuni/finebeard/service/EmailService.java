package bg.softuni.finebeard.service;

/**
 * EmailService interface specifies the contract for sending email notifications
 * related to user registrations.
 */
public interface EmailService {

    /**
     * Sends a registration email to a newly registered user.
     *
     * @param userEmail the email address of the user to whom the registration email will be sent
     * @param userNames the name(s) of the user, which will be included in the registration email
     * @param activationCode the activation code that will be included in the registration email
     */
    void sendRegistrationEmail(
            String userEmail,
            String userNames,
            String activationCode);

}
