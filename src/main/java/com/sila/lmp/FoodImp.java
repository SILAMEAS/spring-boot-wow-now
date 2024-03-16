package com.sila.lmp;

import com.sila.model.Category;
import com.sila.model.Food;
import com.sila.model.Restaurant;
import com.sila.repository.FoodRepository;
import com.sila.request.CreateFoodReq;
import com.sila.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodImp implements FoodService {
    private final FoodRepository foodRepository;
    @Override
    public Food createFood(CreateFoodReq food, Category category, Restaurant restaurant) throws Exception {
        Food food_create = new Food();
        food_create.setFoodCategory(category);
        food_create.setRestaurant(restaurant);
        food_create.setDescription(food.getDescription());
        food_create.setImages(food.getImages());
        food_create.setName(food.getName());
        food_create.setPrice(food.getPrice());
        food_create.setCreationDate(new Date());
        food_create.setSeasonal(food.isSeasional());
        food_create.setVegetarian(food.isVegetarin());
        food_create.setIngredientsItems(food.getIngredients());
        Food saveFood=foodRepository.save(food_create);
        restaurant.getFoods().add(saveFood);
        return saveFood;
    }

    @Override
    public Void deleteFoodById(Long id) throws Exception {
        Food foodByID = findFoodById(id);
        foodByID.setRestaurant(null);
        foodRepository.save(foodByID);
        throw  new Exception("Food id "+id+" was remove from restaurant");
    }

    @Override
    public List<Food> getRestaurantFood(Long restaurantId, boolean isVegin, boolean isNonveg, boolean isSeasional, String foodCategory) {
        List<Food> foodInRestaurant = foodRepository.findFoodByRestaurantId(restaurantId);
        if(isVegin){
            foodInRestaurant=foodInRestaurant.stream().filter(Food::isVegetarian).collect(Collectors.toList());
        }
        if(isNonveg){
            foodInRestaurant=foodInRestaurant.stream().filter(f->!f.isVegetarian()).collect(Collectors.toList());
        }
        if(isSeasional){
            foodInRestaurant=foodInRestaurant.stream().filter(Food::isSeasonal).collect(Collectors.toList());
        }
        if(Objects.isNull(foodInRestaurant)&& !foodCategory.isEmpty()){
            foodInRestaurant=foodInRestaurant.stream().filter(f->{
                if(!Objects.isNull(f.getFoodCategory())){
                    return  f.getFoodCategory().getName().equals(foodCategory);
                }
                return  false;
            }).collect(Collectors.toList());
        }
        return foodInRestaurant;
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
         Optional<Food> isFoodExit= foodRepository.findById(foodId);
         if(isFoodExit.isEmpty()){
             throw  new BadRequestException("Food not found");
         }
        return isFoodExit.get();
    }

    @Override
    public Food updateAvailibilityStatus(Long id) throws Exception {
        Food updateFood = findFoodById(id);
        updateFood.setAvailable(!updateFood.isAvailable());
        return foodRepository.save(updateFood);
    }
}
