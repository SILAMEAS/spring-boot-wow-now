package com.sila.service.lmp;

import com.sila.exception.BadRequestException;
import com.sila.model.Category;
import com.sila.model.Restaurant;
import com.sila.repository.CategoryRepository;
import com.sila.repository.RestaurantRepository;
import com.sila.repository.UserRepository;
import com.sila.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryImp implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    @Override
    public Category createCategory(String name_category, Long userId) {
        Restaurant restaurant=restaurantRepository.findByOwnerId(userId);
        Category category=new Category();
        category.setName(name_category);
        category.setRestaurant(restaurant);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> listCategoriesByRestaurantId(Long restaurant_id) {
        return categoryRepository.findByRestaurantId(restaurant_id);
    }

    @Override
    public Category findCategoryById(Long category_id) throws Exception {
        Optional<Category> categoryExit= categoryRepository.findById(category_id);
        if(categoryExit.isEmpty()){
            throw new BadRequestException("Category id: "+category_id+" not found in database");
        }
        return categoryExit.get();
    }

    @Override
    public Void deleteCategoryById(Long category_id) throws Exception {
        findCategoryById(category_id);
        categoryRepository.deleteById(category_id);
        return null;
    }
}
