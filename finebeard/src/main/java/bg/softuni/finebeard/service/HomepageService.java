package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.entity.ProductEntity;

import java.util.List;

public interface HomepageService {
    List<ProductEntity> getHomepageProducts();
    void updateHomepageProducts(List<ProductEntity> products);

}
