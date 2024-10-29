package bg.softuni.finebeard.service.schedulers;

import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.repository.ProductRepository;
import bg.softuni.finebeard.service.HomepageService;
import bg.softuni.finebeard.service.ProductsRotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the ProductsRotationService interface that is responsible for rotating products displayed on the homepage.
 *
 * This service rotates the products by randomly selecting a subset from the available products in the repository
 * and updating the homepage to display these products. The rotation is scheduled to run at fixed intervals.
 */
@Service
public class ProductsRotationServiceImpl implements ProductsRotationService {

    private static final Logger log = LoggerFactory.getLogger(ProductsRotationServiceImpl.class);
    private final ProductRepository productRepository;
    private final HomepageService homepageService;



    public ProductsRotationServiceImpl(ProductRepository productRepository, HomepageService homepageService) {
        this.productRepository = productRepository;
        this.homepageService = homepageService;
    }


    /**
     * Rotates the products displayed on the homepage by randomly selecting a subset
     * from the available products in the repository and updating the homepage to display these products.
     * This method is invoked at fixed intervals.
     *
     * The number of products to select is defined as 3, but if there are fewer products
     * available, all available products will be selected.
     *
     * The method retrieves all available products, shuffles them to ensure random selection,
     * and updates the homepage with the selected products by invoking the HomepageService.
     *
     * Logs the completion of the homepage update.
     */
    @Override
    @Scheduled(fixedRate = 7200000)
    public void rotateProducts() {
        List<ProductEntity> allProducts = productRepository.findAll();
        if (allProducts.isEmpty()) {
            return;
        }

        int numberOfProducts = 3;



        Collections.shuffle(allProducts);

        List<ProductEntity> selectedProducts = allProducts.stream()
                .limit(Math.min(numberOfProducts, allProducts.size()))
                .collect(Collectors.toList());

        homepageService.updateHomepageProducts(selectedProducts);
        log.info("Home page products updated!");
    }
}
