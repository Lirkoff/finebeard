package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.dto.AddProductDTO;
import bg.softuni.finebeard.model.dto.ProductDetailDTO;
import bg.softuni.finebeard.model.entity.BrandEntity;
import bg.softuni.finebeard.model.entity.CategoryEntity;
import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.repository.BrandRepository;
import bg.softuni.finebeard.repository.CategoryRepository;
import bg.softuni.finebeard.repository.ProductRepository;
import bg.softuni.finebeard.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the ProductService interface that provides methods for managing products.
 */
@Service
public class ProductServiceImpl implements ProductService {

    /**
     * Repository for performing CRUD operations on ProductEntity instances.
     * It leverages JpaRepository to provide standard data access functionality.
     * This repository is utilized to interact with the underlying database and perform
     * operations such as finding, saving, deleting, and listing products.
     */
    private final ProductRepository productRepository;

    /**
     * Repository for performing CRUD operations on Brand entities.
     * Utilized for accessing brand-related data from the data store.
     * It extends JpaRepository to provide standard methods for database interaction.
     */
    private final BrandRepository brandRepository;

    /**
     * Repository for managing CategoryEntity instances.
     * This repository provides CRUD operations and
     * query methods to interact with the underlying
     * data store of category entities.
     *
     * Used within the ProductServiceImpl class to perform
     * operations related to product categories.
     */
    private final CategoryRepository categoryRepository;



    /**
     * Constructs a new instance of ProductServiceImpl with the specified repositories.
     *
     * @param productRepository the repository for product entities
     * @param brandRepository the repository for brand entities
     * @param categoryRepository the repository for category entities
     */
    public ProductServiceImpl(ProductRepository productRepository,
                              BrandRepository brandRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Adds a new product based on the provided data transfer object and returns the UUID of the created product.
     *
     * @param addProductDTO Data Transfer Object containing the details of the product to be added.
     * @return the UUID of the newly added product.
     */
    @Override
    public UUID addProduct(AddProductDTO addProductDTO) {
        ProductEntity product = map(addProductDTO);
        product = productRepository.save(product);


        return product.getUuid();
    }

    /**
     * Retrieves the details of a product using its unique identifier (UUID).
     *
     * @param uuid the unique identifier of the product
     * @return an Optional containing ProductDetailDTO if the product is found, otherwise an empty Optional
     */
    @Override
    public Optional<ProductDetailDTO> getProductDetail(UUID uuid) {
        return productRepository
                .findByUuid(uuid)
                .map(this::mapAsDetails);
    }

    /**
     * Deletes a product with the specified UUID from the repository.
     *
     * @param uuid the UUID of the product to be deleted
     */
    @Override
    @Transactional
    public void deleteProduct(UUID uuid) {
        productRepository.deleteByUuid(uuid);
    }

    /**
     * Retrieves a ProductEntity by its unique identifier (UUID).
     *
     * @param uuid the unique identifier of the product to retrieve
     * @return the ProductEntity associated with the specified UUID, or null if no product is found
     */
    @Override
    public ProductEntity getByUuid(UUID uuid) {
        return productRepository.findByUuid(uuid).orElse(null);
    }

    /**
     * Saves the given product entity to the underlying data store.
     *
     * @param product The product entity to be saved.
     */
    @Override
    public void save(ProductEntity product) {
        productRepository.save(product);
    }

    /**
     * Maps a ProductEntity to a ProductDetailDTO.
     *
     * @param productEntity the product entity to be mapped
     * @return a ProductDetailDTO containing the details of the product entity
     */
    private ProductDetailDTO mapAsDetails(ProductEntity productEntity) {
        return new ProductDetailDTO(
                productEntity.getUuid().toString(),
                productEntity.getDescription(),
                productEntity.getBrand().getName().getDisplayName(),
                productEntity.getModel(),
                productEntity.getImageUrl(),
                productEntity.getPrice()

        );
    }

    /**
     * Maps the provided AddProductDTO to a ProductEntity. This method performs lookups
     * for existing brands and categories, creating and saving new ones if they do not exist.
     *
     * @param addProductDTO Data Transfer Object containing the details needed to create a ProductEntity.
     * @return a new ProductEntity populated with the values from the provided AddProductDTO.
     */
    private ProductEntity map(AddProductDTO addProductDTO) {

        BrandEntity brand = brandRepository.findByName(addProductDTO.name())
                .orElse(new BrandEntity().setName(addProductDTO.name()));


        CategoryEntity category = categoryRepository.findByName(addProductDTO.category())
                        .orElse(new CategoryEntity().setName(addProductDTO.category()));
        categoryRepository.save(category);


        brandRepository.save(brand);

        return new ProductEntity()
                .setUuid(UUID.randomUUID())
                .setBrand(brand)
                .setModel(addProductDTO.model())
                .setCategory(category)
                .setDescription(addProductDTO.description())
                .setPrice(BigDecimal.valueOf(addProductDTO.price()))
                .setImageUrl(addProductDTO.imageUrl());
    }
}
