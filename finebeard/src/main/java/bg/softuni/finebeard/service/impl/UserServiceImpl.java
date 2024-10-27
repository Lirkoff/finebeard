package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.dto.UserEmailRolesDTO;
import bg.softuni.finebeard.model.dto.UserRegistrationDTO;
import bg.softuni.finebeard.model.entity.UserEntity;
import bg.softuni.finebeard.model.enums.AuthProvider;
import bg.softuni.finebeard.model.enums.UserRoleEnum;
import bg.softuni.finebeard.model.events.UserRegisteredEvent;
import bg.softuni.finebeard.repository.UserActivationCodeRepository;
import bg.softuni.finebeard.repository.UserRepository;
import bg.softuni.finebeard.repository.UserRoleRepository;
import bg.softuni.finebeard.service.EmailService;
import bg.softuni.finebeard.service.UserService;

import bg.softuni.finebeard.service.exception.UserAlreadyExistsException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;


/**
 * UserServiceImpl is an implementation of the UserService interface that provides
 * functionalities related to user management and authentication.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * The UserRepository used to persist and retrieve user-related data from the database.
     * It provides various methods to interact with the user entities, such as finding users by email
     * or provider ID, and retrieving all users with their roles.
     */
    private final UserRepository userRepository;

    /**
     * The repository responsible for performing CRUD operations on user roles.
     * Used by the UserServiceImpl class to manage roles associated with the users.
     */
    private final UserRoleRepository userRoleRepository;

    /**
     * The encoder used for handling password encoding and validation.
     *
     * This field is initialized during the construction of the UserServiceImpl class
     * and is used across various methods to encode and validate user passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * The ApplicationEventPublisher instance used for publishing application events within the UserServiceImpl class.
     * This variable facilitates interaction with the event management system by allowing the application to
     * announce specific events, such as user registration or activation, to a broader audience.
     */
    private final ApplicationEventPublisher appEventPublisher;

    /**
     * A service for retrieving detailed user information for authentication purposes.
     * This service is configured to work specifically within the Finebeard application.
     * It helps in loading user-specific data during the authentication process by
     * delegating to the UserDetailsService implementation.
     */
    private final UserDetailsService finebeardUserDetailsService;

    /**
     * A string constant containing all lowercase alphabetic characters.
     * Used in various operations where lowercase letters are required,
     * such as generating random strings or validating user input.
     */
    private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";

    /**
     * A constant representing the uppercase alphabet characters.
     * It is derived by converting the CHAR_LOWERCASE string to uppercase.
     */
    private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();

    /**
     * A constant string containing all numeric digits from 0 to 9.
     * This is used for various operations where numeric characters are needed.
     */
    private static final String DIGIT = "0123456789";

    /**
     * A string containing a set of special characters used for password generation
     * and validation within the UserServiceImpl class.
     */
    private static final String SPECIAL_CHAR = "!@#$%^&*()_-+=<>?";

    /**
     * A constant string that defines the base character set allowed for password generation.
     * It includes lowercase letters, uppercase letters, digits, and special characters.
     */
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + SPECIAL_CHAR;

    /**
     * A SecureRandom instance used to generate cryptographically strong random values.
     * This is primarily utilized for operations needing a high level of randomness
     * and security, such as password generation and secure tokens.
     */
    private static final SecureRandom random = new SecureRandom();

    /**
     * Constructs a new instance of UserServiceImpl using the provided dependencies.
     *
     * @param userRepository the repository used for accessing user entities
     * @param userRoleRepository the repository used for accessing user role entities
     * @param passwordEncoder the encoder used for encoding user passwords
     * @param appEventPublisher the event publisher used for publishing application events
     * @param finebeardUserDetailsService the service used for loading user-specific data
     * @param userActivationCodeRepository the repository used for managing user activation codes
     * @param emailService the service used for sending email notifications
     */
    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder,
                           ApplicationEventPublisher appEventPublisher,
                           UserDetailsService finebeardUserDetailsService,
                           UserActivationCodeRepository userActivationCodeRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.appEventPublisher = appEventPublisher;
        this.finebeardUserDetailsService = finebeardUserDetailsService;
    }

    /**
     * Registers a new user by saving the user information to the repository
     * and publishing a UserRegisteredEvent.
     *
     * @param userRegistrationDTO the DTO containing user registration details
     */
    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        userRepository.save(map(userRegistrationDTO));

        appEventPublisher.publishEvent(new UserRegisteredEvent(
                "UserService",
                userRegistrationDTO.email(),
                userRegistrationDTO.fullName()
        ));

    }

    /**
     * Adds the ADMIN role to a user identified by their email address.
     *
     * @param email The email address of the user to whom the ADMIN role will be added.
     */
    @Override
    public void addUserRole(String email) {
        UserEntity user = userRepository.findByEmail(email).get();

        user.getRoles().add(userRoleRepository.getByRole(UserRoleEnum.ADMIN));

        userRepository.save(user);
    }

    /**
     * Removes the ADMIN role from a user identified by their email address.
     *
     * @param email the email address of the user whose ADMIN role is to be removed
     */
    @Override
    public void removeUserRole(String email) {
        UserEntity user = userRepository.findByEmail(email).get();

        user.getRoles().remove(userRoleRepository.getByRole(UserRoleEnum.ADMIN));

        userRepository.save(user);
    }

    /**
     * Retrieves a paginated list of all user email addresses and their associated roles.
     *
     * @param pageable the pagination information specifying page number, size, and sorting
     * @return a paginated list of {@code UserEmailRolesDTO} containing user email addresses and their roles
     */
    @Override
    @Transactional
    public Page<UserEmailRolesDTO> getAllUsersNamesAndRoles(Pageable pageable) {
        Page<UserEntity> usersPage = userRepository.findAll(pageable);


        Page<UserEmailRolesDTO> dtoPage = usersPage.map(user -> {
            String roles = user.getRoles().stream()
                    .map(r -> r.getRole().name())
                    .sorted()
                    .collect(Collectors.joining(","));
            return new UserEmailRolesDTO(user.getEmail(), roles);
        });

        return dtoPage;
    }

    /**
     * Creates a new user if a user with the given provider ID does not already exist.
     * If a user with the given email exists, a UserAlreadyExistsException is thrown.
     *
     * @param email the email address of the user to check or create
     * @param names the full name of the user, expected to be in the format "First Last"
     * @param providerId the ID provided by the authentication provider
     * @param authProvider the authentication provider (e.g., LOCAL, GOOGLE, FACEBOOK)
     * @throws UserAlreadyExistsException if a user with the given email already exists
     */
    @Override
    public void createUserIfDoesNotExist(String email, String names, String providerId, AuthProvider authProvider) {
        UserEntity byProviderId = userRepository
                .findByProviderId(providerId).orElse(null);

        if (byProviderId == null) {
            if (userRepository.findByEmail(email).isPresent()) {
                throw new UserAlreadyExistsException("User with email " + email + " already exists.");
            } else {
                String randomPassword = generateRandomPassword(10);
                userRepository.save(new UserEntity()
                        .setFirstName(names.split(" ")[0])
                        .setLastName(names.split(" ")[1])
                        .setEmail(email)
                        .setAuthProvider(authProvider)
                        .setProviderId(providerId)
                        .setPassword(passwordEncoder.encode(randomPassword))
                        .setActive(true)
                        .setRoles(userRoleRepository.getByRole(UserRoleEnum.USER)));
            }
        }


    }

    /**
     * Authenticates a user based on their email.
     *
     * @param email The email of the user attempting to log in
     * @return The authentication object containing authentication details of the logged-in user
     */
    @Override
    public Authentication login(String email) {
        UserDetails userDetails = finebeardUserDetailsService.loadUserByUsername(email);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());


        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }

    /**
     * Changes the username of a user.
     *
     * @param currentUserName The current username of the user.
     * @param newUserName The new username to be set for the user.
     */
    @Override
    public void changeUsername(String currentUserName, String newUserName) {
        Optional<UserEntity> user = userRepository.findByEmail(currentUserName);

        user.get().setEmail(newUserName);

        userRepository.save(user.get());
    }


    /**
     * Maps a UserRegistrationDTO object to a UserEntity object.
     *
     * @param userRegistrationDTO the UserRegistrationDTO object containing the user's registration information
     * @return a UserEntity object with the mapped information from the UserRegistrationDTO
     */
    private UserEntity map(UserRegistrationDTO userRegistrationDTO) {
        return new UserEntity()
                .setActive(false)
                .setFirstName(userRegistrationDTO.firstName())
                .setLastName(userRegistrationDTO.lastName())
                .setEmail(userRegistrationDTO.email())
                .setAuthProvider(AuthProvider.LOCAL)
                .setPassword(passwordEncoder.encode(userRegistrationDTO.password()))
                .setRoles(userRoleRepository.getByRole(UserRoleEnum.USER));
    }

    /**
     * Generates a random password of a specified length.
     *
     * @param length the length of the generated password
     * @return a randomly generated password containing at least one
     *         lowercase letter, one uppercase letter, one digit, and one special character
     */
    private String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder(length);

        // Ensure password contains at least one character of each type
        password.append(CHAR_LOWERCASE.charAt(random.nextInt(CHAR_LOWERCASE.length())));
        password.append(CHAR_UPPERCASE.charAt(random.nextInt(CHAR_UPPERCASE.length())));
        password.append(DIGIT.charAt(random.nextInt(DIGIT.length())));
        password.append(SPECIAL_CHAR.charAt(random.nextInt(SPECIAL_CHAR.length())));

        for (int i = 4; i < length; i++) {
            password.append(PASSWORD_ALLOW_BASE.charAt(random.nextInt(PASSWORD_ALLOW_BASE.length())));
        }

        return password.toString();
    }
}
