package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.UserRegistrationDTO;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);
}
