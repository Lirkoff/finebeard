package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.entity.CategoryEntity;

import java.util.Set;

public interface CategoryService {
    Set<CategoryEntity> getAllCategories();
}
