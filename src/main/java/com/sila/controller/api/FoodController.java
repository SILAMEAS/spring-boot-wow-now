package com.sila.controller.api;

import com.sila.model.Food;
import com.sila.service.FoodService;
import com.sila.service.RestaurantService;
import com.sila.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/foods")
@RequiredArgsConstructor
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
        return new ResponseEntity<>(foodService.getRestaurantFood(restaurantId,vegetarian,nonveg,seasanal,food_category),HttpStatus.OK);
    }
}
