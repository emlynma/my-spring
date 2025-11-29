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
public enum RefundStatus {

    INITIAL(0, "INITIAL"),
    SUCCESS(1, "SUCCESS"),
    FAILURE(2, "FAILURE"),
    ;

    private static final Map<Integer, RefundStatus> STATUS_MAPPING = Arrays.stream(values())
            .collect(Collectors.toMap(RefundStatus::getCode, Function.identity()));

    private static final List<RefundStatus> FINAL_STATUSES = List.of(SUCCESS, FAILURE);

    private static final Map<RefundStatus, Set<RefundStatus>> STATUS_MACHINE = Map.of(
            INITIAL, Set.of(SUCCESS, FAILURE)
    );

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String  desc;

    public static RefundStatus valueOf(Integer code) {
        return STATUS_MAPPING.get(code);
    }

    public static boolean checkFinal(RefundStatus status) {
        return FINAL_STATUSES.contains(status);
    }

    public static boolean checkReachability(RefundStatus from, RefundStatus to) {
        return STATUS_MACHINE.getOrDefault(from, Collections.emptySet()).contains(to);
    }

}
