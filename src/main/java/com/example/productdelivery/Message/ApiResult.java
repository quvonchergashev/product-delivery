package com.example.productdelivery.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResult extends Exception{
    private boolean success;
    private String message;
}
