package com.sila.repository;

import com.sila.model.Food;
import com.sila.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    @Query("SELECT f FROM Restaurant f WHERE f.name LIKE %:keyword% OR f.cuisineType LIKE %:keyword%")
    List<Restaurant> findBySearchQuery(@Param("keyword") String query);
    Restaurant findByOwnerId(Long userId);


}
