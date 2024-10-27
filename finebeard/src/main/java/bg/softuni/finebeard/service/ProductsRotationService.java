package bg.softuni.finebeard.service;

/**
 * Service interface for rotating products displayed on the homepage.
 *
 * This service is responsible for updating the selection of products
 * that are prominently featured on the homepage of the application.
 * It can be scheduled to run periodically and randomly selects a subset
 * of products to display.
 */
public interface ProductsRotationService {
    /**
     * Rotates the products displayed on the homepage.
     *
     * This method updates the selection of products that are prominently
     * featured on the homepage. It should be invoked periodically to ensure
     * a fresh and engaging product display for users. The products are
     * selected randomly from the available pool.
     */
    void rotateProducts();
}
