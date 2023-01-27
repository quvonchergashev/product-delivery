package com.example.productdelivery.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseApi {
    private String message;
    private boolean success;
    private Object data;

    public ResponseApi(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ResponseApi(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }
}
