package com.sila.exception.dto;

import lombok.Data;

@Data
public class MessageResponse {
    private int status;
    private String message;
}
