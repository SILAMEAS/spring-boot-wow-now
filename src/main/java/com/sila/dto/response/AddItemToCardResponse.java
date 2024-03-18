package com.sila.dto.response;

import lombok.Data;

@Data
public class AddItemToCardResponse {
  private Long id;
  private Long totalPrice;
  private FoodResponse foodResponse;
  private Long qty;
}
