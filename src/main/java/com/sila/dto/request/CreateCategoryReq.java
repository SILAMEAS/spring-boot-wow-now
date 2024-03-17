package com.sila.dto.request;

import lombok.Data;

@Data
public class CreateCategoryReq {
    private String name;
    private Long userId;
}
