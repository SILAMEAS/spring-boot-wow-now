package com.sila.controller.admin;

import com.sila.model.Restaurant;
import com.sila.model.User;
import com.sila.repository.RestaurantRepository;
import com.sila.dto.request.CreateRestaurantReq;
import com.sila.dto.response.MessageResponse;
import com.sila.service.RestaurantService;
import com.sila.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/restaurants")
@RequiredArgsConstructor
@Slf4j
public class AdminRestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final RestaurantRepository restaurantRepository;
    @GetMapping("/get")
    public ResponseEntity<String> findDD(){
        return new ResponseEntity<>("Can access to /api/admin/restaurant/get",HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantReq req, @RequestHeader("Authorization") String jwt) throws Exception {
        User findUser = userService.findUserByJwtToken(jwt);
        Restaurant userAlreadyHaveRestaurant = restaurantRepository.findByOwnerId(findUser.getId());
//        User already have restaurant
        if(!Objects.isNull(userAlreadyHaveRestaurant)){
            throw new BadRequestException("User already have restaurant");
        }
        try {
            Restaurant restaurant= restaurantService.createRestaurant(req,findUser);
            return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantReq req,@RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {
        User findUser = userService.findUserByJwtToken(jwt);
        Optional<Restaurant> findRestaurantById = restaurantRepository.findById(id);
        Restaurant findRestaurantByOwnerId =  restaurantRepository.findByOwnerId(findUser.getId());
        boolean RestaurantBelongToUser= Objects.equals(findRestaurantById.get().getOwner(), findRestaurantByOwnerId.getOwner());
        if(RestaurantBelongToUser){
            Restaurant restaurant= restaurantService.updateRestaurant(id,req);
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        }
        throw  new BadRequestException("This user isn't owner of restaurant");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(@RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {
        userService.findUserByJwtToken(jwt);
        MessageResponse messageResponse=new MessageResponse();
        try{
            restaurantService.deleteRestaurant(id);
            messageResponse.setMessage("delete restaurant id : "+id+" successfully!");
        }catch (Exception e){
            messageResponse.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MessageResponse> updateRestaurantStatus(@RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception {
        User findUser = userService.findUserByJwtToken(jwt);
        Restaurant restaurant= restaurantService.updateRestaurantStatus(id);
        MessageResponse messageResponse=new MessageResponse();
        if(restaurant.isOpen()){
            messageResponse.setMessage("Update successfully. Restaurant id : "+id+" is Open");
        }else {
            messageResponse.setMessage("Update successfully. Restaurant id : "+id+ " is Close");
        }
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserid(@RequestHeader("Authorization") String jwt) throws Exception {
        User findUser = userService.findUserByJwtToken(jwt);
        Restaurant restaurant= restaurantService.getRestaurantByUserId(findUser.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
