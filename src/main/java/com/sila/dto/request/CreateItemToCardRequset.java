package com.sila.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateItemToCardRequset {
    private Long foodId;
    private Integer qty;
    private List<String> ingredients;
    private Long restaurantId;

}
