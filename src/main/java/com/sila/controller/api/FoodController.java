package com.sila.controller.api;

import com.sila.dto.response.FoodResponse;
import com.sila.exception.BadRequestException;
import com.sila.model.Food;
import com.sila.service.FoodService;
import com.sila.service.RestaurantService;
import com.sila.service.UserService;
import com.sila.utlis.enums.EnumSort;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/foods")
@RequiredArgsConstructor
@Slf4j
public class FoodController {
    private final FoodService foodService;
    private final UserService userService;
    private final RestaurantService restaurantService;
    @PostMapping("/search/{keyword}")
    public ResponseEntity<List<Food>> searchFoods(@RequestHeader("Authorization") String jwt,@PathVariable String keyword) throws Exception {
        userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(foodService.searchFood(keyword), HttpStatus.OK);
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> listFoodByRestaurantId(@RequestHeader("Authorization") String jwt,@RequestParam(required = false) boolean vegetarian,@RequestParam(required = false) boolean seasanal,@RequestParam(required = false) boolean nonveg,@RequestParam(required = false) String food_category,@PathVariable Long restaurantId) throws Exception {
        userService.findUserByJwtToken(jwt);
        log.error(String.valueOf(restaurantId));
        if(Objects.isNull(restaurantId)){
            throw new BadRequestException("restaurant is null");
        }
        return new ResponseEntity<>(foodService.getRestaurantFood(restaurantId,vegetarian,nonveg,seasanal,food_category),HttpStatus.OK);
    }
    @GetMapping("/restaurants")
    public ResponseEntity<List<FoodResponse>> listFoodAll(@RequestHeader("Authorization") String jwt,@RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                  @RequestParam(defaultValue = "asc") String sortOrder) throws Exception {
        userService.findUserByJwtToken(jwt);
        List<Food> listFood = foodService.getAllFood(pageNo, pageSize, sortBy, EnumSort.valueOf(sortOrder));
        List<FoodResponse> listFoodResponse = new ArrayList<>();
        for(Food food:listFood){
            FoodResponse foodTemp =new FoodResponse();
            foodTemp.setName(food.getName());
            foodTemp.setId(food.getId());
            foodTemp.setImages(food.getImages());
            foodTemp.setPrice(food.getPrice());
            foodTemp.setRestaurantId(food.getRestaurant().getId());
            listFoodResponse.add(foodTemp);
        }
        return new ResponseEntity<>(listFoodResponse,HttpStatus.OK);
    }
}
