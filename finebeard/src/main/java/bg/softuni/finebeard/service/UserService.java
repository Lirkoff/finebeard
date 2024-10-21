package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.UserEmailRolesDTO;
import bg.softuni.finebeard.model.dto.UserRegistrationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);
    void addUserRole(String email);
    void removeUserRole(String email);
    Page<UserEmailRolesDTO> getAllUsersNamesAndRoles(Pageable pageable);
    void createUserIfDoesNotExist(String email, String names);
    Authentication login(String email);
    void changeUsername(String currentUserName, String newUserName);


}
