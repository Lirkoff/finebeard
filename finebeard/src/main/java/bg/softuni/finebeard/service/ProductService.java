package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.AddProductDTO;
import bg.softuni.finebeard.model.dto.ProductDetailDTO;

import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    UUID addProduct(AddProductDTO addProductDTO);

    Optional<ProductDetailDTO> getProductDetail(UUID productUUID);

    void deleteProduct(UUID uuid);
}
