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
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;



    public ProductServiceImpl(ProductRepository productRepository,
                              BrandRepository brandRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public UUID addProduct(AddProductDTO addProductDTO) {
        ProductEntity product = map(addProductDTO);
        product = productRepository.save(product);


        return product.getUuid();
    }

    @Override
    public Optional<ProductDetailDTO> getProductDetail(UUID uuid) {
        return productRepository
                .findByUuid(uuid)
                .map(this::mapAsDetails);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID uuid) {
        productRepository.deleteByUuid(uuid);
    }

    @Override
    public ProductEntity getByUuid(UUID uuid) {
        return productRepository.findByUuid(uuid).orElse(null);
    }

    @Override
    public void save(ProductEntity product) {
        productRepository.save(product);
    }


    private ProductDetailDTO mapAsDetails(ProductEntity productEntity) {
        return new ProductDetailDTO(
                productEntity.getUuid().toString(),
                productEntity.getDescription(),
                productEntity.getBrand().getName().getDisplayName(),
                productEntity.getBrand().getModel(),
                productEntity.getImageUrl(),
                productEntity.getPrice()

        );
    }

    private ProductEntity map(AddProductDTO addProductDTO) {

        BrandEntity brand = brandRepository.findByName(addProductDTO.name())
                .orElse(new BrandEntity().setName(addProductDTO.name())).setModel(addProductDTO.model());

        CategoryEntity category = categoryRepository.findByName(addProductDTO.category())
                        .orElse(new CategoryEntity().setName(addProductDTO.category()));
        categoryRepository.save(category);


        brandRepository.save(brand);

        return new ProductEntity()
                .setUuid(UUID.randomUUID())
                .setBrand(brand)
                .setCategory(category)
                .setDescription(addProductDTO.description())
                .setPrice(BigDecimal.valueOf(addProductDTO.price()))
                .setImageUrl(addProductDTO.imageUrl());
    }
}
