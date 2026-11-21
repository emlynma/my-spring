package com.emlyn.spring.data.domain.enums.status;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public enum ExchangeStatus {

    INITIAL(0, "INITIAL"),
    SUCCESS(1, "SUCCESS"),
    FAILURE(2, "FAILURE"),
    ;

    private static final List<ExchangeStatus> FINAL_STATUSES = List.of(SUCCESS, FAILURE);

    private static final Map<ExchangeStatus, Set<ExchangeStatus>> STATUS_MACHINE = Map.of(
            INITIAL, Set.of(SUCCESS, FAILURE)
    );

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String  desc;

    public static boolean checkFinal(ExchangeStatus status) {
        return FINAL_STATUSES.contains(status);
    }

    public static boolean checkReachability(ExchangeStatus from, ExchangeStatus to) {
        return STATUS_MACHINE.getOrDefault(from, Collections.emptySet()).contains(to);
    }

}
