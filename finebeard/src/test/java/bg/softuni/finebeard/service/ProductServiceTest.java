package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.dto.ProductDetailDTO;
import bg.softuni.finebeard.model.entity.ProductEntity;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    ProductService productService = mock(ProductService.class);

    /**
     * Test the getProductDetail method of ProductService class.
     * Scenario: When getProductDetail is called with a valid UUID, it should return an Optional of ProductDetailDTO.
     */
    @Test
    public void testGetProductDetail_ValidUUID() {
        UUID testUUID = UUID.randomUUID();
        ProductDetailDTO productDetailDTO = new ProductDetailDTO("1", "description", "brand", "model", "imageUrl", null);

        when(productService.getProductDetail(testUUID)).thenReturn(Optional.of(productDetailDTO));

        Optional<ProductDetailDTO> result = productService.getProductDetail(testUUID);

        verify(productService, times(1)).getProductDetail(testUUID);
        assertEquals("description", result.get().description());
        assertEquals("brand", result.get().brand());
        assertEquals("model", result.get().model());
        assertEquals("imageUrl", result.get().imageUrl());
    }

    /**
     * Test the getProductDetail method of ProductService class.
     * Scenario: When getProductDetail is called with a UUID that is not associated with a product, it should return an empty Optional.
     */
    @Test
    public void testGetProductDetail_InvalidUUID() {
        UUID testUUID = UUID.randomUUID();

        when(productService.getProductDetail(testUUID)).thenReturn(Optional.empty());

        Optional<ProductDetailDTO> result = productService.getProductDetail(testUUID);

        verify(productService, times(1)).getProductDetail(testUUID);
        assertEquals(Optional.empty(), result);
    }
}