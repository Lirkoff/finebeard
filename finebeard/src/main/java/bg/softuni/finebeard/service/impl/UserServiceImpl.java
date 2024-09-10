package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.dto.UserRegistrationDTO;
import bg.softuni.finebeard.model.entity.UserEntity;
import bg.softuni.finebeard.model.enums.UserRoleEnum;
import bg.softuni.finebeard.model.events.UserRegisteredEvent;
import bg.softuni.finebeard.repository.UserRepository;
import bg.softuni.finebeard.repository.UserRoleRepository;
import bg.softuni.finebeard.service.UserService;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class  UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher appEventPublisher;
    private final UserDetailsService finebeardUserDetailsService;

    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder,
                           ApplicationEventPublisher appEventPublisher,
                           UserDetailsService finebeardUserDetailsService) {
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
    public Map<String, String> getAllUsersNamesAndRoles() {
        List<UserEntity> data = userRepository.findAll();

        Map<String, String> result = new HashMap<>();

        data.forEach(e -> {
            List<String> roles = new ArrayList<>();
            e.getRoles().forEach(r -> roles.add(r.getRole().name()));
            result.put(e.getEmail(), String.join(", ", roles));
        });

        return result;
    }

    @Override
    public void createUserIfNotExists(String email, String names) {
        // Create manually user in the DB
        // password not necessary
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
                .setPassword(passwordEncoder.encode(userRegistrationDTO.password()))
                .setRoles(userRoleRepository.getByRole(UserRoleEnum.USER));
    }
}
