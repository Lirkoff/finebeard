package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.repository.ProductRepository;
import bg.softuni.finebeard.service.ShopService;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    private final ProductRepository productRepository;

    public ShopServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }





}
