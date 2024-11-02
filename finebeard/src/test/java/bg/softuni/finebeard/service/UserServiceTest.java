package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.UserRegistrationDTO;
import bg.softuni.finebeard.model.enums.AuthProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserService userService;

    @AfterEach
    public void init() {
        Mockito.reset(userService);
    }

    @Test
    public void test_registerUser_validRegistration() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("John", "Doe", "john@doe.com", "password", "password");
        Mockito.doNothing().when(userService).registerUser(userRegistrationDTO);

        userService.registerUser(userRegistrationDTO);

        Mockito.verify(userService, Mockito.times(1)).registerUser(userRegistrationDTO);
    }

    @Test
    public void test_registerUser_emptyName() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("", "", "john@doe.com", "password", "password");
        Mockito.doNothing().when(userService).registerUser(userRegistrationDTO);

        userService.registerUser(userRegistrationDTO);

        Mockito.verify(userService, Mockito.times(1)).registerUser(userRegistrationDTO);
    }

    @Test
    public void test_registerUser_mismatchedPassword() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("John", "Doe", "john@doe.com", "password", "differentPassword");
        Mockito.doNothing().when(userService).registerUser(userRegistrationDTO);

        userService.registerUser(userRegistrationDTO);

        Mockito.verify(userService, Mockito.times(1)).registerUser(userRegistrationDTO);
    }

    @Test
    public void test_registerUser_duplicateEmail() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO("John", "Doe", "john@doe.com", "password", "password");
        Mockito.doNothing().when(userService).registerUser(userRegistrationDTO);

        userService.registerUser(userRegistrationDTO);
        userService.registerUser(userRegistrationDTO);

        Mockito.verify(userService, Mockito.times(2)).registerUser(userRegistrationDTO);
    }
}