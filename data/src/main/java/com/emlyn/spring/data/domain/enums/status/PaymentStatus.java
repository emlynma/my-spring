package com.emlyn.spring.data.domain.enums.status;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    INITIAL(0, "INITIAL"),
    SUCCESS(1, "SUCCESS"),
    FAILURE(2, "FAILURE"),
    PENDING(3, "PENDING")
    ;

    private static final Map<Integer, PaymentStatus> STATUS_MAPPING = Arrays.stream(values())
            .collect(Collectors.toMap(PaymentStatus::getCode, Function.identity()));

    private static final List<PaymentStatus> FINAL_STATUSES = List.of(SUCCESS, FAILURE);

    private static final Map<PaymentStatus, Set<PaymentStatus>> STATUS_MACHINE = Map.of(
            INITIAL, Set.of(PENDING, FAILURE),
            PENDING, Set.of(SUCCESS, FAILURE)
    );

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String  desc;

    public static PaymentStatus valueOf(Integer code) {
        return STATUS_MAPPING.get(code);
    }

    public static boolean checkFinal(PaymentStatus status) {
        return FINAL_STATUSES.contains(status);
    }

    public static boolean checkReachability(PaymentStatus from, PaymentStatus to) {
        return STATUS_MACHINE.getOrDefault(from, Collections.emptySet()).contains(to);
    }

}
