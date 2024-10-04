package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.entity.CategoryEntity;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    List<CategoryEntity> getAllCategories();


    CategoryEntity getById(Long categoryId);
}
