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


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher appEventPublisher;
    private final UserDetailsService finebeardUserDetailsService;

    private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
    private static final String DIGIT = "0123456789";
    private static final String SPECIAL_CHAR = "!@#$%^&*()_-+=<>?";
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + SPECIAL_CHAR;

    private static final SecureRandom random = new SecureRandom();

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

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        userRepository.save(map(userRegistrationDTO));

        appEventPublisher.publishEvent(new UserRegisteredEvent(
                "UserService",
                userRegistrationDTO.email(),
                userRegistrationDTO.fullName()
        ));

    }

    @Override
    public void addUserRole(String email) {
        UserEntity user = userRepository.findByEmail(email).get();

        user.getRoles().add(userRoleRepository.getByRole(UserRoleEnum.ADMIN));

        userRepository.save(user);
    }

    @Override
    public void removeUserRole(String email) {
        UserEntity user = userRepository.findByEmail(email).get();

        user.getRoles().remove(userRoleRepository.getByRole(UserRoleEnum.ADMIN));

        userRepository.save(user);
    }

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

    @Override
    public void changeUsername(String currentUserName, String newUserName) {
        Optional<UserEntity> user = userRepository.findByEmail(currentUserName);

        user.get().setEmail(newUserName);

        userRepository.save(user.get());
    }


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
