package com.emlynma.ms.core.base;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class ApiResponse<T> {

    private String code;
    private String desc;
    private T      data;

    public ApiResponse(@NonNull ErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.desc = errorCode.getDesc();
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(BaseErrorCode.SUCCESS, data);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode, null);
    }

}
