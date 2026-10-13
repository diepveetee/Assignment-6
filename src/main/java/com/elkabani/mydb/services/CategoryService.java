package com.elkabani.mydb.services;

import com.elkabani.mydb.repositories.CategoryRepository;
import org.springframework.stereotype.Service;


//  CategoryRepository is your repository interface.

//  Spring automatically creates an implementation of that interface.

//  Spring passes the repository object into your CategoryService constructor.

//  You can now use categoryRepository to save, find, or delete categories.



@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
