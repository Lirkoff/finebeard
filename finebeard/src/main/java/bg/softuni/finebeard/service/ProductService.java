package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.AddProductDTO;
import bg.softuni.finebeard.model.dto.ProductDetailDTO;
import bg.softuni.finebeard.model.entity.ProductEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for managing products.
 */
public interface ProductService {
    /**
     * Adds a new product based on the provided data transfer object and returns the UUID of the created product.
     *
     * @param addProductDTO Data Transfer Object containing the details of the product to be added.
     * @return the UUID of the newly added product.
     */
    UUID addProduct(AddProductDTO addProductDTO);

    /**
     * Retrieves the details of a product using its unique identifier.
     *
     * @param productUUID the unique identifier of the product
     * @return an Optional containing ProductDetailDTO if the product is found, otherwise an empty Optional
     */
    Optional<ProductDetailDTO> getProductDetail(UUID productUUID);

    /**
     * Deletes a product with the specified UUID.
     *
     * @param uuid the UUID of the product to be deleted
     */
    void deleteProduct(UUID uuid);

    /**
     * Retrieves a ProductEntity by its unique identifier (UUID).
     *
     * @param uuid the unique identifier of the product to retrieve
     * @return the ProductEntity associated with the specified UUID, or null if no product is found
     */
    ProductEntity getByUuid(UUID uuid);

    /**
     * Saves the given product entity to the underlying data store.
     *
     * @param product The product entity to be saved.
     */
    void save(ProductEntity product);


}
