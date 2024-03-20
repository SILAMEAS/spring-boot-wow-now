package com.sila.dto.request;

import com.sila.model.Address;
import com.sila.model.ContactInformation;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;

@Data
public class CreateRestaurantReq {
  private Long id;
  @NotEmpty(message = "name can't be empty")
  private String name;
  @NotEmpty(message = "description can't be empty")
  private String description;
  private String cuisineType;
  private Address address;
  private ContactInformation contactInformation;
  private String openingHours;
  private List<String> images;
}

