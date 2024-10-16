package bg.softuni.finebeard.repository;


import bg.softuni.finebeard.model.entity.ProductEntity;
import bg.softuni.finebeard.model.enums.ProductCategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByUuid(UUID productUUID);

    void deleteByUuid(UUID uuid);

    Page<ProductEntity> findAllByCategoryId(Long categoryId, Pageable pageable);



//    Page<ProductEntity> findAllByCategoryName(ProductCategoryEnum categoryName, Pageable pageable);


}
