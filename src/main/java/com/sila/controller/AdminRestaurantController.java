package com.sila.controller;

import com.sila.model.Restaurant;
import com.sila.model.User;
import com.sila.request.CreateRestaurantReq;
import com.sila.response.MessageResponse;
import com.sila.service.RestaurantService;
import com.sila.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin/restaurant")
@RequiredArgsConstructor
@Slf4j
public class AdminRestaurantController {
    private RestaurantService restaurantService;
    private UserService userService;
    @GetMapping("/get")
    public ResponseEntity<String> findDD(){
        return new ResponseEntity<>("HHI",HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantReq req,@RequestHeader("Authorization") String jwt) throws Exception {
        User findUser = userService.findUserByJwtToken(jwt);
        Restaurant restaurant= restaurantService.createRestaurant(req,findUser);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantReq req,@RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {
        User findUser = userService.findUserByJwtToken(jwt);
        Restaurant restaurant= restaurantService.updateRestaurant(id,req);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(@RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {
        User findUser = userService.findUserByJwtToken(jwt);
        restaurantService.deleteRestaurant(id);
        MessageResponse messageResponse=new MessageResponse();
        messageResponse.setMessage("sdf");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MessageResponse> updateRestaurantStatus(@RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {
        User findUser = userService.findUserByJwtToken(jwt);
        Restaurant restaurant= restaurantService.updateRestaurantStatus(id);
        MessageResponse messageResponse=new MessageResponse();
        messageResponse.setMessage("Update successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Restaurant> findRestaurantByUserid(@RequestHeader("Authorization") String jwt) throws Exception {
        User findUser = userService.findUserByJwtToken(jwt);
        Restaurant restaurant= restaurantService.getRestaurantByUserId(findUser.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
