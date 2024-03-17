package com.sila.service;

import com.sila.model.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(String name_category,Long userId);
    public List<Category> listCategoriesByRestaurantId(Long restaurant_id);
    public Category findCategoryById(Long category_id)throws Exception;
    public Void deleteCategoryById(Long category_id)throws Exception;
}
