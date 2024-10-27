package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.repository.ProductRepository;
import bg.softuni.finebeard.service.MonitoringService;
import bg.softuni.finebeard.service.ShopService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * ShopServiceImpl is an implementation of the ShopService interface.
 * This service interacts with the product repository to retrieve product data
 * and utilizes the monitoring service to log search activities.
 */
@Service
public class ShopServiceImpl implements ShopService {

    /**
     * The ProductRepository instance used to interact with the product data storage.
     * This repository provides methods for retrieving, deleting, and paginating product entities
     * based on various criteria such as category ID and product UUID.
     */
    private final ProductRepository productRepository;

    /**
     * The monitoringService is responsible for logging various events related to shop operations,
     * particularly product searches. It is used to track and monitor these activities for
     * analytical and monitoring purposes.
     */
    private final MonitoringService monitoringService;

    /**
     * Constructs a new instance of the ShopServiceImpl.
     *
     * @param productRepository the repository to interact with for product data
     * @param monitoringService the service used for logging search activities
     */
    public ShopServiceImpl(ProductRepository productRepository, MonitoringService monitoringService) {
        this.productRepository = productRepository;
        this.monitoringService = monitoringService;
    }


    /**
     * Retrieves a paginated list of all products in a specified category.
     * This method logs a product search event before performing the retrieval.
     *
     * @param categoryId the ID of the category to fetch products from
     * @param pageable the pagination information
     * @return a paginated list of products in the specified category
     */
    @Override
    @Transactional
    public Page<ProductEntity> getAllProducts(Long categoryId, Pageable pageable) {
        monitoringService.logProductSearch();
        return productRepository
                .findAllByCategoryId(categoryId,pageable);
    }
}
