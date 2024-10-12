package bg.softuni.finebeard.init;

import bg.softuni.finebeard.model.entity.BrandEntity;
import bg.softuni.finebeard.model.entity.CategoryEntity;
import bg.softuni.finebeard.model.enums.BrandEnum;
import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import bg.softuni.finebeard.repository.BrandRepository;
import bg.softuni.finebeard.repository.CategoryRepository;
import bg.softuni.finebeard.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class AppInit implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final UserService userService;

    public AppInit(CategoryRepository categoryRepository, BrandRepository brandRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        //DB init

    }
}
