package com.sila.dto.response;

import com.sila.model.Address;
import lombok.Data;

@Data
public class RestaurantResponse {
    private Long id;
    private String name;
    private String address;
    private Boolean favorite;
    private Boolean open;
    private String description;
    private String openingHours;
}
