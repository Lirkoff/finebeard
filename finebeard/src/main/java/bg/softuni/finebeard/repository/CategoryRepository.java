package bg.softuni.finebeard.repository;


import bg.softuni.finebeard.model.entity.CategoryEntity;
import bg.softuni.finebeard.model.enums.ProductCategoryEnum;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByName(ProductCategoryEnum category);


}
