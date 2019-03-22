package com.marom.recipemongo.services;

import com.marom.recipemongo.domain.Category;

import java.util.Set;

public interface CategoryService {

    Set<Category> getAllCategories();

    Category findById(String id);
}
