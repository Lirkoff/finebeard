package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.entity.ProductEntity;

import java.util.List;

/**
 * Service interface for managing products displayed on the homepage.
 *
 * This service provides methods to retrieve and update the list of
 * products that are showcased on the homepage.
 */
public interface HomepageService {
    /**
     * Retrieves a list of products to be displayed on the homepage.
     *
     * @return a list of ProductEntity objects representing the products showcased on the homepage.
     */
    List<ProductEntity> getHomepageProducts();

    /**
     * Updates the list of products displayed on the homepage.
     *
     * @param products the list of ProductEntity objects to be showcased on the homepage.
     */
    void updateHomepageProducts(List<ProductEntity> products);

}
