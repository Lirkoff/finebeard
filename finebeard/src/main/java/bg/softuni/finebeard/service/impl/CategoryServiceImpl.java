package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.entity.CategoryEntity;
import bg.softuni.finebeard.repository.CategoryRepository;
import bg.softuni.finebeard.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<CategoryEntity> getAllCategories() {
        return new HashSet<>(categoryRepository.findAll());
    }
}
