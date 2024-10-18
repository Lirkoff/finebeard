package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.service.HomepageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomepageServiceImpl implements HomepageService {
    private List<ProductEntity> homepageProducts;

    @Override
    public List<ProductEntity> getHomepageProducts() {
        return homepageProducts;
    }

    @Override
    public void updateHomepageProducts(List<ProductEntity> products) {
        this.homepageProducts = products;
    }
}
