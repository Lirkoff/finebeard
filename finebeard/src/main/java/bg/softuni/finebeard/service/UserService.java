package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.UserEmailRolesDTO;
import bg.softuni.finebeard.model.dto.UserRegistrationDTO;
import bg.softuni.finebeard.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.TreeMap;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);
    void addUserRole(String email);
    void removeUserRole(String email);
    Page<UserEmailRolesDTO> getAllUsersNamesAndRoles(Pageable pageable);
    void createUserIfNotExists(String email, String names);
    Authentication login(String email);
    void changeUsername(String currentUserName, String newUserName);

    boolean activateUser(String activationCode);
}
