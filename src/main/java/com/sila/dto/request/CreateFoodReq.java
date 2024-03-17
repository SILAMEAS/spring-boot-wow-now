package com.sila.dto.request;

import com.sila.model.Category;
import com.sila.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodReq {
    private  String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images;
    private  Long restaurantId;
    private boolean vegetarin;
    private boolean seasional;
    private List<IngredientsItem> ingredients;

}
