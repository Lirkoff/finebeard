package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.dto.UserRegistrationDTO;
import bg.softuni.finebeard.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        System.out.println(userRegistrationDTO);
    }
}
