package com.naraci.core.entity;

import lombok.Data;

/**
 * @author ShenZhaoYu
 * @date 2024/2/14
 */
@Data
public class ApiResponse {
    private int code;
    private String message;
    private Object data;

    public ApiResponse(int code, String message, Object data) {

        this.code = code;
        this.message = message;
        this.data = data;
    }
}
