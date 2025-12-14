package com.emlyn.spring.common.contract;

import com.emlyn.spring.common.error.ErrorCode;
import com.emlyn.spring.common.error.SysErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
        return new ApiResponse<>(SysErrorCode.SUCCESS, data);
    }

    public static <T> ApiResponse<T> failure(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode, null);
    }

    public boolean successful() {
        return SysErrorCode.SUCCESS.getCode().equals(this.code);
    }

}
