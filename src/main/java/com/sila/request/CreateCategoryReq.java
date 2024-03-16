package com.sila.request;

import lombok.Data;

@Data
public class CreateCategoryReq {
    private String name;
    private Long userId;
}
