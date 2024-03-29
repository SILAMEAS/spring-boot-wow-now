package com.sila.controller.api;

import com.sila.dto.PaginationRestaurantDto;
import com.sila.dto.RestaurantDto;
import com.sila.dto.mapper.FoodDto;
import com.sila.dto.pagination.EntityResponseHandler;
import com.sila.dto.response.RestaurantResponse;
import com.sila.exception.BadRequestException;
import com.sila.model.Restaurant;
import com.sila.model.User;
import com.sila.service.RestaurantService;
import com.sila.service.UserService;
import com.sila.utlis.enums.EnumSort;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    @GetMapping()
    public ResponseEntity< List<RestaurantResponse>> getAllRestaurant(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(restaurantService.getRestaurants(user.getFavourites()), HttpStatus.OK);
    }
    @GetMapping("/pagination")
    public ResponseEntity<EntityResponseHandler<PaginationRestaurantDto>> listFoodAll(
            @RequestHeader("Authorization") String jwt, @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) throws Exception {
        userService.findUserByJwtToken(jwt);
        Page<PaginationRestaurantDto> paginationRestaurantDto= restaurantService.getPaginationRestaurants(pageNo, pageSize, sortBy,
                EnumSort.valueOf(sortOrder)).map(fuck -> this.modelMapper.map(fuck, PaginationRestaurantDto.class));
        return new ResponseEntity<>(new EntityResponseHandler<>(paginationRestaurantDto), HttpStatus.OK);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity< List<Restaurant>> searchRestaurantByKeyword(@RequestHeader("Authorization") String jwt, @PathVariable String keyword) throws Exception {
        userService.findUserByJwtToken(jwt);
       try {
           List<Restaurant> searchRestaurant= restaurantService.searchRestaurant(keyword);
           return new ResponseEntity<>(searchRestaurant, HttpStatus.OK);
       }catch (Exception e){
           throw new BadRequestException("search restaurant not found "+">>"+e.getMessage());
       }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {
        userService.findUserByJwtToken(jwt);
        try {
         Restaurant getRestaurantById= restaurantService.findRestaurantById(id);
            return new ResponseEntity<>(getRestaurantById, HttpStatus.OK);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto> addRestaurantToFavorites(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {
        User userLogin=userService.findUserByJwtToken(jwt);
        try {
            RestaurantDto dto= restaurantService.addRestaurantToFavorites(id,userLogin);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
