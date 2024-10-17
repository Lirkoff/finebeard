package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.entity.CategoryEntity;
import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import bg.softuni.finebeard.repository.ProductRepository;
import bg.softuni.finebeard.service.MonitoringService;
import bg.softuni.finebeard.service.ShopService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;

@Service
public class ShopServiceImpl implements ShopService {

    private final ProductRepository productRepository;
    private final MonitoringService monitoringService;

    public ShopServiceImpl(ProductRepository productRepository, MonitoringService monitoringService) {
        this.productRepository = productRepository;
        this.monitoringService = monitoringService;
    }


    @Override
    @Transactional
    public Page<ProductEntity> getAllProducts(Long categoryId, Pageable pageable) {
        monitoringService.logProductSearch();


        return productRepository
                .findAllByCategoryId(categoryId,pageable);
    }
}
