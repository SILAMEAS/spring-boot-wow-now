package com.sila.repository;

import com.sila.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
//    @Query("SELECT r FROM Restaurant WHERE lower(r.name) LIKE concat('%',:query,'%') OR lower(r.cuisineType) LIKE concat('%',:query,'%')")
//    List<Restaurant> findBySearchQuery(String query);
    Restaurant findByOwnerId(Long userId);


}
