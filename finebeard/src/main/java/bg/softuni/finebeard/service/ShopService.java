package bg.softuni.finebeard.service;


import bg.softuni.finebeard.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * ShopService interface provides methods for managing shop-related operations
 * such as retrieving all products within a specific category.
 */
public interface ShopService {

    /**
     * Retrieves a paginated list of all products in a specified category.
     *
     * @param id the ID of the category to fetch products from
     * @param pageable the pagination information
     * @return a paginated list of products in the specified category
     */
    Page<ProductEntity> getAllProducts(Long id, Pageable pageable);

}
