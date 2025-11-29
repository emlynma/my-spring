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
public enum RechargeStatus {

    INITIAL(0, "INITIAL"),
    SUCCESS(1, "SUCCESS"),
    REFUSED(2, "REFUSED"),
    ;

    private static final Map<Integer, RechargeStatus> STATUS_MAPPING = Arrays.stream(values())
            .collect(Collectors.toMap(RechargeStatus::getCode, Function.identity()));

    private static final List<RechargeStatus> FINAL_STATUSES = List.of(SUCCESS, REFUSED);

    private static final Map<RechargeStatus, Set<RechargeStatus>> STATUS_MACHINE = Map.of(
            INITIAL, Set.of(SUCCESS, REFUSED)
    );

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String  desc;

    public static RechargeStatus valueOf(Integer code) {
        return STATUS_MAPPING.get(code);
    }

    public static boolean checkFinal(RechargeStatus status) {
        return FINAL_STATUSES.contains(status);
    }

    public static boolean checkReachability(RechargeStatus from, RechargeStatus to) {
        return STATUS_MACHINE.getOrDefault(from, Collections.emptySet()).contains(to);
    }

}
