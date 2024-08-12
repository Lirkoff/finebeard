package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.entity.ProductEntity;

public interface ShopService {

    void addProduct(ProductEntity product);


    void removeProduct(ProductEntity product);
}
