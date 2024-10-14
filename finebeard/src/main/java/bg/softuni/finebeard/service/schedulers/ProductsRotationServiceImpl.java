package bg.softuni.finebeard.service.schedulers;

import bg.softuni.finebeard.model.dto.ProductDetailDTO;
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

@Service
public class ProductsRotationServiceImpl implements ProductsRotationService {

    private static final Logger log = LoggerFactory.getLogger(ProductsRotationServiceImpl.class);
    private final ProductRepository productRepository;
    private final HomepageService homepageService;



    public ProductsRotationServiceImpl(ProductRepository productRepository, HomepageService homepageService) {
        this.productRepository = productRepository;
        this.homepageService = homepageService;
    }


    // Schedule the method to run every 1 minute (60000 ms)
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
