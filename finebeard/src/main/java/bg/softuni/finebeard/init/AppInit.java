package bg.softuni.finebeard.init;

import bg.softuni.finebeard.model.entity.BrandEntity;
import bg.softuni.finebeard.model.entity.CategoryEntity;
import bg.softuni.finebeard.model.enums.BrandEnum;
import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import bg.softuni.finebeard.repository.BrandRepository;
import bg.softuni.finebeard.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AppInit implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public AppInit(CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //DB init
        if (brandRepository.count() == 0 && categoryRepository.count() == 0) {
            for (ProductCategoryEnum productCategoryEnum : ProductCategoryEnum.values()) {
                CategoryEntity category = new CategoryEntity();
                category.setName(productCategoryEnum);
                categoryRepository.save(category);
            }

            for (BrandEnum brand : BrandEnum.values()) {
                BrandEntity brandEntity = new BrandEntity();
                brandEntity.setName(brand);
                brandEntity.setModels(new ArrayList<>());
                brandRepository.save(brandEntity);
            }
        }

    }
}
