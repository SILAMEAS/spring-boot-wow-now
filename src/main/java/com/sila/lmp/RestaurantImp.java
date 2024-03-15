package com.sila.lmp;

import com.sila.dto.RestaurantDto;
import com.sila.model.Address;
import com.sila.model.Restaurant;
import com.sila.model.User;
import com.sila.repository.AddressRepository;
import com.sila.repository.RestaurantRepository;
import com.sila.repository.UserRepository;
import com.sila.request.CreateRestaurantReq;
import com.sila.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantImp implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantReq req, User user) throws Exception {
        Address address=addressRepository.save(req.getAddress());
        Restaurant restaurant=new Restaurant();
        restaurant.setAddress(address);
        restaurant.setName(req.getName());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setDescription(req.getDescription());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long Id, CreateRestaurantReq updateRestaurant) throws Exception {
        Restaurant restaurant=findRestaurantById(Id);
        if(!Objects.isNull(updateRestaurant.getName())){
            restaurant.setName(updateRestaurant.getName());
        }
        if(!Objects.isNull(updateRestaurant.getDescription())){
            restaurant.setDescription(updateRestaurant.getDescription());
        }
        if(!Objects.isNull(updateRestaurant.getCuisineType())){
            restaurant.setCuisineType(updateRestaurant.getCuisineType());
        }
        if(!Objects.isNull(updateRestaurant.getImages())){
            restaurant.setImages(updateRestaurant.getImages());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) throws Exception {
        Restaurant isRestaurantExit= findRestaurantById(id);
        if(isRestaurantExit==null){
            throw new BadRequestException("this restaurant is don't have in our database");
        }
        restaurantRepository.delete(isRestaurantExit);
    }

    @Override
    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }
//    @Override
//    public List<Restaurant> searchRestaurant(String keyword) {
//        return restaurantRepository.findBySearchQuery(keyword);
//    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> restaurantExit=restaurantRepository.findById(id);
        if(restaurantExit.isEmpty()){
            throw new Exception("restaurant not found");
        }
        return restaurantExit.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurantOfUser = restaurantRepository.findByOwnerId(userId);
        if(restaurantOfUser==null){
            throw new Exception("this user "+userId+" don't have restaurant");
        }

        return restaurantOfUser;
    }

    @Override
    public RestaurantDto addRestaurantToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant findRestaurant = findRestaurantById(restaurantId);
        RestaurantDto dto=new RestaurantDto();
        dto.setDescription(findRestaurant.getDescription());
        dto.setId(findRestaurant.getId());
        dto.setImages(findRestaurant.getImages());
        if(user.getFavourites().contains(dto)){
            user.getFavourites().remove(dto);
        }else user.getFavourites().add(dto);
        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant findRestaurant=findRestaurantById(restaurantId);
        findRestaurant.setOpen(!findRestaurant.isOpen());
       restaurantRepository.save(findRestaurant);
       return restaurantRepository.save(findRestaurant);
    }
}
