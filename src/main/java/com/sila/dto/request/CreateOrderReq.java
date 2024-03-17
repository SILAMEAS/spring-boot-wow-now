package com.sila.dto.request;

import com.sila.model.Address;
import lombok.Data;

@Data
public class CreateOrderReq {
    private Long restaurantId;
    private Address deliveryAddress;
}
