package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.UserRegistrationDTO;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);
    void addUserRole(String email);
    void removeUserRole(String email);
    Map<String, String> getAllUsersNamesAndRoles();
}
