package com.sila.service;

import com.sila.dto.RestaurantDto;
import com.sila.model.Restaurant;
import com.sila.model.User;
import com.sila.request.CreateRestaurantReq;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantReq req, User user) throws Exception;
    public Restaurant updateRestaurant(Long restaurantId,CreateRestaurantReq updateRestaurant) throws Exception;
    public void deleteRestaurant(Long id)throws Exception;
    public List<Restaurant> getRestaurants();
//    public  List<Restaurant> searchRestaurant(String keyword);
    public Restaurant findRestaurantById(Long id) throws Exception;
    public Restaurant getRestaurantByUserId(Long userId) throws Exception;
    public RestaurantDto addRestaurantToFavorites(Long restaurantId,User user)throws Exception;
    public Restaurant updateRestaurantStatus(Long restaurantId)throws Exception;
}
