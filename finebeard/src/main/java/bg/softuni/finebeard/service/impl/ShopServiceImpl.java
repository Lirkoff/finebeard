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

    @Override
    public void addProduct(ProductEntity product) {
        productRepository.save(product);
    }

    @Override
    public void removeProduct(ProductEntity product) {
        productRepository.delete(product);
    }



}
