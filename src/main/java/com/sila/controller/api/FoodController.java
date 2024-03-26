package com.sila.controller.api;

import com.sila.dto.pagination.EntityResponseHandler;
import com.sila.dto.mapper.FoodDto;
import com.sila.exception.BadRequestException;
import com.sila.model.Food;
import com.sila.service.FoodService;
import com.sila.service.RestaurantService;
import com.sila.service.UserService;
import com.sila.utlis.enums.EnumSort;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/foods")
@RequiredArgsConstructor
@Slf4j
public class FoodController {

  private final FoodService foodService;
  private final UserService userService;
  private final RestaurantService restaurantService;
  private final ModelMapper modelMapper;

  @PostMapping("/search/{keyword}")
  public ResponseEntity<List<Food>> searchFoods(@RequestHeader("Authorization") String jwt,
      @PathVariable String keyword) throws Exception {
    userService.findUserByJwtToken(jwt);
    return new ResponseEntity<>(foodService.searchFood(keyword), HttpStatus.OK);
  }

  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<List<Food>> listFoodByRestaurantId(
      @RequestHeader("Authorization") String jwt,
      @RequestParam(required = false) boolean vegetarian,
      @RequestParam(required = false) boolean seasanal,
      @RequestParam(required = false) boolean nonveg,
      @RequestParam(required = false) String food_category, @PathVariable Long restaurantId)
      throws Exception {
    userService.findUserByJwtToken(jwt);
    log.error(String.valueOf(restaurantId));
    if (Objects.isNull(restaurantId)) {
      throw new BadRequestException("restaurant is null");
    }
    return new ResponseEntity<>(
        foodService.getRestaurantFood(restaurantId, vegetarian, nonveg, seasanal, food_category),
        HttpStatus.OK);
  }

  @GetMapping("/restaurants")
  public ResponseEntity<EntityResponseHandler<FoodDto>> listFoodAll(
      @RequestHeader("Authorization") String jwt, @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortOrder) throws Exception {
    userService.findUserByJwtToken(jwt);
    Page<FoodDto> foods = foodService.getAllFood(pageNo, pageSize, sortBy,
        EnumSort.valueOf(sortOrder)).map(fuck -> this.modelMapper.map(fuck, FoodDto.class));
    return new ResponseEntity<>(new EntityResponseHandler<>(foods), HttpStatus.OK);
  }

}
