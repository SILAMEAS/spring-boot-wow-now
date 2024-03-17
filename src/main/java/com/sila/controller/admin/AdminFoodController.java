package com.sila.controller.admin;

import com.sila.model.Category;
import com.sila.model.Food;
import com.sila.model.Restaurant;
import com.sila.dto.request.CreateFoodReq;
import com.sila.dto.response.MessageResponse;
import com.sila.service.CategoryService;
import com.sila.service.FoodService;
import com.sila.service.RestaurantService;
import com.sila.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/foods")
@RequiredArgsConstructor
public class AdminFoodController {
    private final FoodService foodService;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final CategoryService categoryService;
    @PostMapping()
    public ResponseEntity<Food> createFood(@RequestHeader("Authorization") String jwt, @RequestBody CreateFoodReq req) throws Exception {
        userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
        Category category=categoryService.findCategoryById(req.getCategoryId());
        Food food=foodService.createFood(req,category,restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {
        userService.findUserByJwtToken(jwt);
        foodService.deleteFoodById(id);
        MessageResponse message=new MessageResponse();
        message.setMessage("Food was delete from restaurant");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @PutMapping("/{id}/avaibility-status")
    public ResponseEntity<Food> updateAvailibilityStatus(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {
        userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(foodService.updateAvailibilityStatus(id),HttpStatus.OK);
    }
}
