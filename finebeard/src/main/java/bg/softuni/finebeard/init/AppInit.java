package bg.softuni.finebeard.init;

import bg.softuni.finebeard.model.dto.UserRegistrationDTO;
import bg.softuni.finebeard.model.entity.BrandEntity;
import bg.softuni.finebeard.model.entity.CategoryEntity;
import bg.softuni.finebeard.model.entity.UserEntity;
import bg.softuni.finebeard.model.entity.UserRolesEntity;
import bg.softuni.finebeard.model.enums.BrandEnum;
import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import bg.softuni.finebeard.model.enums.UserRoleEnum;
import bg.softuni.finebeard.repository.BrandRepository;
import bg.softuni.finebeard.repository.CategoryRepository;
import bg.softuni.finebeard.repository.UserRepository;
import bg.softuni.finebeard.repository.UserRoleRepository;
import bg.softuni.finebeard.service.UserService;
import ch.qos.logback.classic.encoder.JsonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class AppInit implements CommandLineRunner {


    private final String masterPassword;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserRoleRepository userRoleRepository;


    public AppInit(CategoryRepository categoryRepository,
                   BrandRepository brandRepository,
                   UserService userService,
                   @Value("${finebeard.default.master.pass}") String masterPassword,
                   UserRepository userRepository,
                   PasswordEncoder encoder, UserRoleRepository userRoleRepository) {
        this.masterPassword = masterPassword;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //DB init

        if (userRoleRepository.count() == 0) {
            for (UserRoleEnum value : UserRoleEnum.values()) {
                userRoleRepository.save(new UserRolesEntity().setRole(value));
            }
        }

        if (userRepository.count() == 0) {

            UserEntity userEntity = new UserEntity()
                    .setFirstName("Master")
                    .setLastName("Masterov")
                    .setEmail("master@example.com")
                    .setActive(true)
                    .setPassword(encoder.encode(masterPassword))
                    .setRoles(userRoleRepository.getByRole(UserRoleEnum.MASTER));

            userEntity.getRoles().add(userRoleRepository.getByRole(UserRoleEnum.ADMIN));
            userEntity.getRoles().add(userRoleRepository.getByRole(UserRoleEnum.USER));

            userRepository.save(userEntity);

        }


    }
}
