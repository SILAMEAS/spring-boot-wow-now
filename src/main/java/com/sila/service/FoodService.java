package com.sila.service;

import com.sila.model.Category;
import com.sila.model.Food;
import com.sila.model.Restaurant;
import com.sila.dto.request.CreateFoodReq;
import com.sila.utlis.enums.EnumSort;

import java.util.List;
import org.springframework.data.domain.Page;

public interface FoodService {
    public Food createFood(CreateFoodReq food, Category category, Restaurant restaurant)throws Exception;

    public Void deleteFoodById(Long id)throws Exception;
    public List<Food> getRestaurantFood(Long restaurantId,boolean isVegin,
                                        boolean isNonveg,boolean isSeasional,
                                        String foodCategory);
    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId)throws Exception;
    public Food updateAvailibilityStatus(Long id)throws Exception;
    public Page<Food> getAllFood(Integer pageNo, Integer pageSize, String sortBy, EnumSort sortOder);
    public Food updateFoodById(Long foodId,CreateFoodReq req)throws Exception;

}
