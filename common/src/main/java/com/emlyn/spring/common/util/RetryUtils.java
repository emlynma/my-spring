package com.emlyn.spring.common.util;

import lombok.experimental.UtilityClass;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@UtilityClass
public class RetryUtils {

    private static final int DEFAULT_RETRY_COUNT = 1;
    private static final int DEFAULT_SLEEP_MILLI = 0;

    public static <T> T execute(Supplier<T> supplier) {
        return execute(supplier, null, null, DEFAULT_RETRY_COUNT, DEFAULT_SLEEP_MILLI);
    }

    public static <T> T execute(Supplier<T> supplier, Function<Exception, T> fallback) {
        return execute(supplier, fallback, null, DEFAULT_RETRY_COUNT, DEFAULT_SLEEP_MILLI);
    }

    public static <T> T execute(Supplier<T> supplier, Function<Exception, T> fallback, Predicate<T> predicate) {
        return execute(supplier, fallback, predicate, DEFAULT_RETRY_COUNT, DEFAULT_SLEEP_MILLI);
    }

    public static <T> T execute(Supplier<T> supplier, Function<Exception, T> fallback, Predicate<T> predicate,
                                int retryCount, long sleepMillis) {
        T result = null;
        Exception lastException = null;
        for (int i = 0; i <= retryCount; i++) {
            try {
                // 重试间隔
                if (i > 0 && sleepMillis > 0) {
                    Thread.sleep(sleepMillis);
                }
                // 执行方法
                result = supplier.get();
                // 检查结果
                if (predicate == null || predicate.test(result)) {
                    return result; // 正常返回
                }
            } catch (Exception e) {
                lastException = e; // 记录最后一次异常
            }
        }
        // 失败处理
        if ((lastException != null || !predicate.test(result)) && fallback != null) {
            return fallback.apply(lastException);
        }
        // 返回结果
        return result;
    }
}
