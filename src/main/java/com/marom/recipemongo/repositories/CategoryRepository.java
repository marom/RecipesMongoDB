package com.marom.recipemongo.repositories;

import com.marom.recipemongo.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {
}
