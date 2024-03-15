package com.sila.request;

import com.sila.model.Address;
import com.sila.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantReq {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;
}

