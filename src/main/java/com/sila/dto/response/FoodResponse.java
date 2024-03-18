package com.sila.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class FoodResponse {
  private Long id;
  private String name;
  private Long price;
  private List<String> images;
  private Long restaurantId;
}
