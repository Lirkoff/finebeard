package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.entity.ActivationAttemptEntity;
import bg.softuni.finebeard.model.entity.UserActivationCodeEntity;
import bg.softuni.finebeard.model.events.UserRegisteredEvent;
import bg.softuni.finebeard.model.rate_limiting.RateLimiterPerIp;
import bg.softuni.finebeard.repository.ActivationAttemptRepository;
import bg.softuni.finebeard.repository.UserActivationCodeRepository;
import bg.softuni.finebeard.repository.UserRepository;
import bg.softuni.finebeard.service.EmailService;
import bg.softuni.finebeard.service.MonitoringService;
import bg.softuni.finebeard.service.UserActivationService;
import bg.softuni.finebeard.service.exception.ObjectNotFoundException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;


/**
 * Implementation of the `UserActivationService` interface, providing methods to handle
 * user activation processes, including sending activation emails, generating activation codes,
 * and activating user accounts.
 */
@Service
public class UserActivationServiceImpl implements UserActivationService {

    /**
     * A constant string containing the characters used for generating activation codes.
     * The string includes lowercase and uppercase alphabetic characters, as well as numeric digits.
     * This constant is used by the {@link UserActivationServiceImpl#generateActivationCode()} method
     * to create random activation codes for user account activation.
     */
    private static final String ACTIVATION_CODE_SYMBOLS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * Specifies the length of the activation code generated for user activation.
     * This value determines the number of characters in the activation code.
     */
    private static final int ACTIVATION_CODE_LENGTH = 20;

    /**
     * EmailService is responsible for handling email-related operations such as
     * sending registration emails to newly registered users for activation purposes.
     */
    private final EmailService emailService;

    /**
     * Repository interface for managing user entities in the database.
     * Provides methods to interact with user data, such as finding users by email,
     * fetching all users with their roles, and finding users by provider ID.
     * Utilized in the user activation service for accessing user-related data.
     */
    private final UserRepository userRepository;

    /**
     * Repository for accessing and managing user activation codes in the database.
     * This repository provides methods for retrieving activation codes by their value
     * and for deleting activation codes based on their creation time.
     *
     * Used within the UserActivationServiceImpl class to facilitate operations related
     * to user activation processes, such as creating, validating, and cleaning up
     * activation codes.
     */
    private final UserActivationCodeRepository userActivationCodeRepository;

    /**
     * Repository for handling activation attempts.
     * Used to manage persistence and retrieval of activation attempt records.
     */
    private final ActivationAttemptRepository activationAttemptRepository;

    /**
     * Constructs a new instance of UserActivationServiceImpl with the necessary dependencies.
     *
     * @param emailService the service responsible for sending email notifications
     * @param userRepository the repository handling user data persistence and retrieval
     * @param userActivationCodeRepository the repository managing activation code entities
     * @param activationAttemptRepository the repository for tracking activation attempts
     * @param monitoringService the service used for logging and monitoring various events
     */
    public UserActivationServiceImpl(EmailService emailService,
                                     UserRepository userRepository,
                                     UserActivationCodeRepository userActivationCodeRepository,
                                     ActivationAttemptRepository activationAttemptRepository,
                                     MonitoringService monitoringService) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.userActivationCodeRepository = userActivationCodeRepository;
        this.activationAttemptRepository = activationAttemptRepository;
    }

    /**
     * Handles the event triggered upon user registration by generating an activation code
     * and sending a registration email to the user.
     *
     * @param event The event triggered upon user registration, containing user details.
     */
    @Override
    @EventListener(UserRegisteredEvent.class)
    public void userRegistered(UserRegisteredEvent event) {
        String activationCode = createActivationCode(event.getUserEmail());

        emailService.sendRegistrationEmail(event.getUserEmail(), event.getUserNames(), activationCode);
    }

    /**
     * Cleans up obsolete activation entities from the database.
     * This involves removing activation codes that were created more than 24 hours ago.
     *
     * @return The number of obsolete activation entities that were deleted.
     */
    @Override
    public Long cleanUpObsoleteActivationEntities() {
        Instant cutoff = Instant.now().minus(24, ChronoUnit.HOURS);

        return userActivationCodeRepository.deleteByCreatedBefore(cutoff);
    }

    /**
     * Cleans up obsolete activation attempts from the database.
     * Activation attempts older than two days are considered obsolete and will be deleted.
     *
     * @return The number of obsolete activation attempts that were deleted.
     */
    @Override
    public Long cleanUpObsoleteActivationAttempts() {
        Instant cutoff = Instant.now().minus(2, ChronoUnit.DAYS);

        return activationAttemptRepository.deleteByAttemptTimeBefore(cutoff);

    }



    /**
     * Generates a new user activation code and stores it in the database for the user with the specified email.
     *
     * @param userEmail the email of the user for whom the activation code is being created
     * @return the generated activation code
     * @throws ObjectNotFoundException if no user is found with the specified email
     */
    @Override
    public String createActivationCode(String userEmail) {
        String activationCode = generateActivationCode();

        UserActivationCodeEntity userActivationCodeEntity = new UserActivationCodeEntity();
        userActivationCodeEntity.setActivationCode(activationCode);
        userActivationCodeEntity.setCreated(Instant.now());
        userActivationCodeEntity.setUsed(false);
        userActivationCodeEntity.setUser(userRepository.findByEmail(userEmail).orElseThrow(() -> new ObjectNotFoundException("User not found!")));

        userActivationCodeRepository.save(userActivationCodeEntity);

        return activationCode;
    }

    /**
     * Generates a random activation code using a secure random generator.
     *
     * @return a randomly generated activation code as a String
     */
    private static String generateActivationCode() {
        StringBuilder activationCode = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < ACTIVATION_CODE_LENGTH; i++) {
            int randInx = random.nextInt(ACTIVATION_CODE_SYMBOLS.length());
            activationCode.append(ACTIVATION_CODE_SYMBOLS.charAt(randInx));
        }

        return activationCode.toString();
    }


    /**
     * Activates a user account based on the provided activation code.
     * This method will mark the activation code as used, set the user account to active,
     * and log the activation attempt.
     *
     * @param activationCode the activation code used to activate the user account
     * @param ipAddress the IP address from which the activation request originated
     * @return true if the user account was successfully activated, false otherwise
     */
    @Override
    @RateLimiterPerIp(name = "activateUser")
    public boolean activateUser(String activationCode, String ipAddress) {

        UserActivationCodeEntity codeEntity = userActivationCodeRepository.getByActivationCode(activationCode);

        boolean successful = false;


        if (codeEntity != null && isActivationCodeExpired(codeEntity) && !codeEntity.isUsed()) {
            codeEntity.setUsed(true);
            codeEntity.getUser().setActive(true);
            userActivationCodeRepository.save(codeEntity);
            successful = true;
        }

        ActivationAttemptEntity attempt = new ActivationAttemptEntity();
        attempt.setActivationCode(activationCode);
        attempt.setIpAddress(ipAddress);
        attempt.setAttemptTime(Instant.now());
        attempt.setSuccessful(successful);
        activationAttemptRepository.save(attempt);


        return successful;
    }


    /**
     * Checks whether the activation code is expired based on its creation time.
     *
     * @param codeEntity the entity containing the activation code and its associated metadata, including creation time
     * @return true if the activation code is expired (i.e., more than 24 hours have passed since its creation), false otherwise
     */
    private boolean isActivationCodeExpired(UserActivationCodeEntity codeEntity) {
        return codeEntity.getCreated().plus(24, ChronoUnit.HOURS).isAfter(Instant.now());
    }

}
