package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.dto.UserEmailRolesDTO;
import bg.softuni.finebeard.model.dto.UserRegistrationDTO;
import bg.softuni.finebeard.model.entity.UserActivationCodeEntity;
import bg.softuni.finebeard.model.entity.UserEntity;
import bg.softuni.finebeard.model.entity.UserRolesEntity;
import bg.softuni.finebeard.model.enums.UserRoleEnum;
import bg.softuni.finebeard.model.events.UserRegisteredEvent;
import bg.softuni.finebeard.repository.UserActivationCodeRepository;
import bg.softuni.finebeard.repository.UserRepository;
import bg.softuni.finebeard.repository.UserRoleRepository;
import bg.softuni.finebeard.service.UserService;

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

import java.util.*;
import java.util.stream.Collectors;


@Service
public class  UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher appEventPublisher;
    private final UserDetailsService finebeardUserDetailsService;
    private final UserActivationCodeRepository userActivationCodeRepository;

    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder,
                           ApplicationEventPublisher appEventPublisher,
                           UserDetailsService finebeardUserDetailsService, UserActivationCodeRepository userActivationCodeRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.appEventPublisher = appEventPublisher;
        this.finebeardUserDetailsService = finebeardUserDetailsService;
        this.userActivationCodeRepository = userActivationCodeRepository;
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
                    .map(r-> r.getRole().name())
                    .sorted()
                    .collect(Collectors.joining(","));
            return new UserEmailRolesDTO(user.getEmail(), roles);
        });

        return dtoPage;
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

    @Override
    public boolean activateUser(String activationCode) {
        UserActivationCodeEntity codeEntity = userActivationCodeRepository.getByActivationCode(activationCode);

        if (codeEntity == null) {
            return false;
        }

        codeEntity.getUser().setActive(true);
        userRepository.save(codeEntity.getUser());
        userActivationCodeRepository.delete(codeEntity);

        return true;
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
