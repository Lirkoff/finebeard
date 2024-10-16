package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.dto.AddProductDTO;
import bg.softuni.finebeard.model.dto.ProductDetailDTO;
import bg.softuni.finebeard.model.entity.BrandEntity;
import bg.softuni.finebeard.model.entity.CategoryEntity;
import bg.softuni.finebeard.model.entity.ModelEntity;
import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.repository.BrandRepository;
import bg.softuni.finebeard.repository.CategoryRepository;
import bg.softuni.finebeard.repository.ModelRepository;
import bg.softuni.finebeard.repository.ProductRepository;
import bg.softuni.finebeard.service.MonitoringService;
import bg.softuni.finebeard.service.ProductService;
import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ModelRepository modelRepository;



    public ProductServiceImpl(ProductRepository productRepository,
                              BrandRepository brandRepository,
                              CategoryRepository categoryRepository,
                              ModelRepository modelRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.modelRepository = modelRepository;
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
//        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteProduct(UUID uuid) {

    }

    private ProductDetailDTO mapAsDetails(ProductEntity productEntity) {
        ModelEntity model = productEntity.getBrand().getModels().get(productEntity.getBrand().getModels().size() - 1);
        return new ProductDetailDTO(
                productEntity.getUuid().toString(),
                productEntity.getDescription(),
                productEntity.getBrand().getName().getDisplayName(),
                model.getName(),
                productEntity.getImageUrl(),
                productEntity.getPrice()

        );
    }

    private ProductEntity map(AddProductDTO addProductDTO) {
        BrandEntity brand = brandRepository.findByName(addProductDTO.name())
                .orElse(new BrandEntity().setName(addProductDTO.name()).setModels(new ArrayList<>()));

        ModelEntity model = modelRepository.findByName(addProductDTO.model())
                .orElse(new ModelEntity().setName(addProductDTO.model()));
        modelRepository.save(model);

        CategoryEntity category = categoryRepository.findByName(addProductDTO.category())
                        .orElse(new CategoryEntity().setName(addProductDTO.category()));
        categoryRepository.save(category);

        brand.getModels().add(model);

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
