package com.sila.dto;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FoodDto implements Serializable {
  private Long id;
  @NotEmpty
  private String name;
  @NotEmpty
  private Long price;
  @NotEmpty
  private List<String> images;
  @NotEmpty
  private Long restaurantId;
}
