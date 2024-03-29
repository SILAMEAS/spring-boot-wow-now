package com.sila.service.lmp;

import com.sila.dto.RestaurantDto;
import com.sila.dto.request.CreateRestaurantReq;
import com.sila.dto.response.RestaurantResponse;
import com.sila.exception.BadRequestException;
import com.sila.model.Address;
import com.sila.model.Restaurant;
import com.sila.model.User;
import com.sila.repository.AddressRepository;
import com.sila.repository.RestaurantRepository;
import com.sila.repository.UserRepository;
import com.sila.service.RestaurantService;
import com.sila.utlis.enums.EnumSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service@Slf4j
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
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setDescription(req.getDescription());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setImages(req.getImages());
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
        if(!Objects.isNull(updateRestaurant.getImages())){
            restaurant.setImages(updateRestaurant.getImages());
        }
        restaurant.setOpen(updateRestaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long id) throws Exception {
        Restaurant isRestaurantExit= findRestaurantById(id);
        restaurantRepository.delete(isRestaurantExit);
    }

    @Override
    public List<RestaurantResponse> getRestaurants(List<RestaurantDto> favoriteByUser) {
        List<RestaurantResponse> listRestaurantResponses=new ArrayList<>();
        List<Restaurant> restaurantList=restaurantRepository.findAll();
        for(Restaurant restaurant:restaurantList){
            RestaurantResponse temp=new RestaurantResponse();
            temp.setAddress(restaurant.getAddress().getStreetAddress()+", "+restaurant.getAddress().getCity()+", "+restaurant.getAddress().getCountry());
            temp.setOpen(restaurant.isOpen());
            temp.setDescription(restaurant.getDescription());
            temp.setImages(restaurant.getImages());
            temp.setName(restaurant.getName());
            temp.setId(restaurant.getId());
            temp.setOpeningHours(restaurant.getOpeningHours());
            temp.setFavorite(findFavorite(restaurant.getId(),favoriteByUser));
            listRestaurantResponses.add(temp);
        }
        return listRestaurantResponses;
    }
    public boolean findFavorite(
            Long id, List<RestaurantDto> fav) {
        for (RestaurantDto temp : fav) {
            if (temp.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> restaurantExit=restaurantRepository.findById(id);
        if(restaurantExit.isEmpty()){
            throw new BadRequestException("RestaurantId : "+id+" not found in database!");
        }
        return restaurantExit.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurantOfUser = restaurantRepository.findByOwnerId(userId);
        if(restaurantOfUser==null){
            throw new BadRequestException("this user don't have restaurant");
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
        dto.setName(findRestaurant.getName());
        boolean isFavorite=false;
        List<RestaurantDto> favorites=user.getFavourites();
        for(RestaurantDto favorite:favorites){
            if(favorite.getId().equals(restaurantId)){
                isFavorite=true;
                break;
            }

        }
        if(isFavorite){
            favorites.removeIf(fav->fav.getId().equals(restaurantId));
            dto.setMessage("Restaurant was remove form favorite lists");
        }else {
            favorites.add(dto);
            dto.setMessage("Restaurant was add to favorite lists");
        }
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

    @Override
    public Page<Restaurant> getPaginationRestaurants(Integer pageNo, Integer pageSize, String sortBy, EnumSort sortOder) {
        Pageable paging = PageRequest.of(pageNo-1, pageSize,
                sortOder == EnumSort.asc ?
                        Sort.by(sortBy.isEmpty() ? "name" : sortBy).ascending() :
                        Sort.by(sortBy.isEmpty() ? "name" : sortBy).descending());

        return restaurantRepository.findAll(paging);
    }
}
