package com.naraci.core.aop;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ShenZhaoYu
 * @date 2024/2/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {
    private int code;
    private String msg;
    private Object data;
    public CustomException() {

    }

    public CustomException(String msg) {
        this.code = 1;
        this.msg = msg;
        this.data = null;
    }

    public CustomException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
