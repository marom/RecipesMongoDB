package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Category;
import com.marom.recipemongo.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> getAllCategories() {

        Set<Category> allCategories = new HashSet<>();
        categoryRepository.findAll().forEach(allCategories::add);

        return allCategories;
    }
}
