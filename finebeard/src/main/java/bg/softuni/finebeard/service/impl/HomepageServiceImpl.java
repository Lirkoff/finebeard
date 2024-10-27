package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.service.HomepageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the HomepageService interface for managing products displayed on the homepage.
 *
 * This class provides the ability to retrieve and update the list of products that are showcased on the homepage.
 * It maintains an internal list of ProductEntity objects representing the products.
 */
@Service
public class HomepageServiceImpl implements HomepageService {
    /**
     * A list of products that are showcased on the homepage.
     *
     * This list contains ProductEntity objects which represent the products
     * displayed on the homepage. The products are retrieved and updated
     * through methods in the HomepageService implementation.
     */
    private List<ProductEntity> homepageProducts;

    /**
     * Retrieves a list of products to be displayed on the homepage.
     *
     * @return a list of ProductEntity objects representing the products showcased on the homepage.
     */
    @Override
    public List<ProductEntity> getHomepageProducts() {
        return homepageProducts;
    }

    /**
     * Updates the list of products displayed on the homepage.
     *
     * @param products the list of ProductEntity objects to be showcased on the homepage.
     */
    @Override
    public void updateHomepageProducts(List<ProductEntity> products) {
        this.homepageProducts = products;
    }
}
