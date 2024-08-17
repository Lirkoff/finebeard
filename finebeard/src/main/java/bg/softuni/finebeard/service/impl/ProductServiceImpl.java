package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.dto.AddProductDTO;
import bg.softuni.finebeard.model.dto.ProductDetailDTO;
import bg.softuni.finebeard.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public UUID addProduct(AddProductDTO addProductDTO) {
        return null;
    }

    @Override
    public ProductDetailDTO getProductDetail(UUID uuid) {
        return null;
    }

    @Override
    public void deleteProduct(UUID uuid) {

    }
}
