package bg.softuni.finebeard.service;


import bg.softuni.finebeard.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopService {

    Page<ProductEntity> getAllProducts(Pageable pageable);
}
