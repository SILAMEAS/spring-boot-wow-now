package com.sila.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateItemToCardRequset {
    private Long foodId;
    private Integer qty;
    private List<String> ingredients;

}
