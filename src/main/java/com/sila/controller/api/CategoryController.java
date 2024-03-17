package com.sila.controller.api;

import com.sila.dto.request.CreateCategoryReq;
import com.sila.model.Category;
import com.sila.model.Restaurant;
import com.sila.model.User;
import com.sila.service.CategoryService;
import com.sila.service.RestaurantService;
import com.sila.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final RestaurantService restaurantService;
    @PostMapping("/admin/categories")
    public ResponseEntity<Category> createCategory(@RequestHeader("Authorization") String jwt, @RequestBody CreateCategoryReq req) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(categoryService.createCategory(req.getName(),user.getId()), HttpStatus.CREATED);
    }
    @GetMapping("/categories/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.getRestaurantByUserId(user.getId());
        List<Category> categoriesInRestaurant=categoryService.listCategoriesByRestaurantId(restaurant.getId());
        return new ResponseEntity<>(categoriesInRestaurant, HttpStatus.OK);
    }
    @DeleteMapping("/admin/categories/{category_id}")
    public ResponseEntity<String> deleteCategory(@RequestHeader("Authorization") String jwt, @PathVariable Long category_id) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        categoryService.deleteCategoryById(category_id);
        return new ResponseEntity<>("Category id : "+category_id+" deleted", HttpStatus.CREATED);
    }


}
