package com.sila.dto.request;

import lombok.Data;

@Data
public class UpdateCartItemRequest {
    private Long cardItemId;
    private int qty;

}
