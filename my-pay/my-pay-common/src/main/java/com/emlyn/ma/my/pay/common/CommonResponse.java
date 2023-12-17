package com.emlyn.ma.my.pay.common;

import lombok.Data;

@Data
public class CommonResponse<T> {

    private final String code;
    private final String desc;
    private final T data;

    private CommonResponse(ErrorCodeEnum errorCode, T data) {
        this.code = errorCode.getCode();
        this.desc = errorCode.getDesc();
        this.data = data;
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(ErrorCodeEnum.SUCCESS, data);
    }

    public static <T> CommonResponse<T> failure(ErrorCodeEnum errorCode) {
        return new CommonResponse<>(errorCode, null);
    }

}
