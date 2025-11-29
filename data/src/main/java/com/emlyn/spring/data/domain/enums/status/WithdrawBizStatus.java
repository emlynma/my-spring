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
public enum WithdrawBizStatus {

    INITIAL(0, "INITIAL"),
    EXCHANGE_POSTING(1, "EXCHANGE_POSTING"),
    EXCHANGE_POSTING_SUCCESS(2, "EXCHANGE_POSTING_SUCCESS"),
    EXCHANGE_POSTING_FAILURE(3, "EXCHANGE_POSTING_FAILURE"),
    EXCHANGE_REVERSE(4, "EXCHANGE_REVERSE"),
    EXCHANGE_REVERSE_SUCCESS(5, "EXCHANGE_REVERSE_SUCCESS"),
    EXCHANGE_REVERSE_FAILURE(6, "EXCHANGE_REVERSE_FAILURE"),
    ;

    private static final Map<Integer, WithdrawBizStatus> STATUS_MAPPING = Arrays.stream(values())
            .collect(Collectors.toMap(WithdrawBizStatus::getCode, Function.identity()));

    private static final List<WithdrawBizStatus> FINAL_STATUSES = List.of(
            EXCHANGE_POSTING_SUCCESS, EXCHANGE_POSTING_FAILURE,
            EXCHANGE_REVERSE_SUCCESS, EXCHANGE_REVERSE_FAILURE
    );

    private static final Map<WithdrawBizStatus, Set<WithdrawBizStatus>> STATUS_MACHINE = Map.of(
            INITIAL, Set.of(EXCHANGE_POSTING),
            EXCHANGE_POSTING, Set.of(EXCHANGE_POSTING_SUCCESS, EXCHANGE_POSTING_FAILURE, EXCHANGE_REVERSE),
            EXCHANGE_REVERSE, Set.of(EXCHANGE_REVERSE_SUCCESS, EXCHANGE_REVERSE_FAILURE)
    );

    private static final Map<WithdrawBizStatus, PaymentStatus> BIZ_STATUS_MAPPING = Map.of(
            INITIAL, PaymentStatus.INITIAL,
            EXCHANGE_POSTING, PaymentStatus.PENDING,
            EXCHANGE_POSTING_SUCCESS, PaymentStatus.SUCCESS,
            EXCHANGE_POSTING_FAILURE, PaymentStatus.FAILURE,
            EXCHANGE_REVERSE, PaymentStatus.PENDING,
            EXCHANGE_REVERSE_SUCCESS, PaymentStatus.SUCCESS,
            EXCHANGE_REVERSE_FAILURE, PaymentStatus.FAILURE
    );

    @EnumValue
    @JsonValue
    private final Integer code;
    private final String  desc;

    public static WithdrawBizStatus valueOf(Integer code) {
        return STATUS_MAPPING.get(code);
    }

    public static boolean checkFinal(WithdrawBizStatus status) {
        return FINAL_STATUSES.contains(status);
    }

    public static boolean checkReachability(WithdrawBizStatus from, WithdrawBizStatus to) {
        return STATUS_MACHINE.getOrDefault(from, Collections.emptySet()).contains(to);
    }

    public static PaymentStatus getPaymentStatus(WithdrawBizStatus bizStatus) {
        return BIZ_STATUS_MAPPING.get(bizStatus);
    }

}
