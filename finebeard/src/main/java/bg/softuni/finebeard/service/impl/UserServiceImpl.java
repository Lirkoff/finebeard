package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.dto.UserRegistrationDTO;
import bg.softuni.finebeard.model.entity.UserEntity;
import bg.softuni.finebeard.model.enums.UserRoleEnum;
import bg.softuni.finebeard.repository.UserRepository;
import bg.softuni.finebeard.repository.UserRoleRepository;
import bg.softuni.finebeard.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class  UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        userRepository.save(map(userRegistrationDTO));
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

    private UserEntity map(UserRegistrationDTO userRegistrationDTO) {
        return new UserEntity()
                .setFirstName(userRegistrationDTO.firstName())
                .setLastName(userRegistrationDTO.lastName())
                .setEmail(userRegistrationDTO.email())
                .setPassword(passwordEncoder.encode(userRegistrationDTO.password()))
                .setActive(true)
                .setRoles(userRoleRepository.getByRole(UserRoleEnum.USER));
    }
}
