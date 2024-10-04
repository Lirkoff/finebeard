package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.entity.CategoryEntity;
import bg.softuni.finebeard.repository.CategoryRepository;
import bg.softuni.finebeard.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        categories.sort(
                Comparator.comparing(c -> c.getName().getDisplayName()));
        return categories;
    }

    @Override
    public CategoryEntity getById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }
}
