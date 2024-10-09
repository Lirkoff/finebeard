package bg.softuni.finebeard.init;

import bg.softuni.finebeard.model.entity.CategoryEntity;
import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import bg.softuni.finebeard.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class AppInit implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public AppInit(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //DB init
        if (categoryRepository.count() <= 0) {
            for (ProductCategoryEnum value : ProductCategoryEnum.values()) {
                CategoryEntity category = new CategoryEntity();
                category.setName(value);
                category.setImageUrl(category.getName().getDisplayName());
                categoryRepository.save(category);
            }
        }
    }
}
