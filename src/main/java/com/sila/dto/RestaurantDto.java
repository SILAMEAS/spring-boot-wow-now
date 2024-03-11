package com.sila.dto;

import jakarta.persistence.Column;
import java.util.List;
import lombok.Data;

@Data
public class RestaurantDto {
private String title;
@Column(length = 1000)
private List<String> images;
private String description;
private Long id;



}
