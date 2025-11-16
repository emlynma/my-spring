package com.emlynma.ms.data.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RefundStatusEnum {

    INIT(0, "init"),
    SUCCESS(1, "success"),
    FAIL(2, "fail"),
    PENDING(3, "pending"),
    ;

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String desc;

    public static RefundStatusEnum valueOf(Integer code) {
        return Arrays.stream(values())
                .filter(value -> value.getCode().equals(code))
                .findFirst().orElse(null);
    }

    public static boolean finalStatus(RefundStatusEnum status) {
        return SUCCESS == status || FAIL == status;
    }

}
