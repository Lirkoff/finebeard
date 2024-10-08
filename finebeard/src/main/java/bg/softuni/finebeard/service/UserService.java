package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.UserRegistrationDTO;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.TreeMap;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);
    void addUserRole(String email);
    void removeUserRole(String email);
    TreeMap<String, String> getAllUsersNamesAndRoles();
    void createUserIfNotExists(String email, String names);
    Authentication login(String email);
    void changeUsername(String currentUserName, String newUserName);
}
