package com.emlynma.ms.core.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@UtilityClass
public class RetryUtils {

    private static final int DEFAULT_COUNT = 1;
    private static final int DEFAULT_SLEEP = 0;

    public static <T> T execute(Supplier<T> supplier) {
        return execute(supplier, null, null, DEFAULT_COUNT, DEFAULT_SLEEP);
    }

    public static <T> T execute(Supplier<T> supplier, Function<Throwable, T> function) {
        return execute(supplier, function, null, DEFAULT_COUNT, DEFAULT_SLEEP);
    }

    public static <T> T execute(Supplier<T> supplier, Function<Throwable, T> function, Predicate<T> predicate) {
        return execute(supplier, function, predicate, DEFAULT_COUNT, DEFAULT_SLEEP);
    }

    /**
     *
     * @param supplier  方法执行器
     * @param function  异常处理器
     * @param predicate 结果判断器
     * @param count     重试次数
     * @param sleep     重试间隔
     * @return          返回值
     * @param <T>       返回值类型
     */
    public static <T> @Nullable T execute(@NonNull Supplier<T> supplier,
                                          Function<Throwable, T> function,
                                          Predicate<T> predicate,
                                          int count, int sleep) {
        T result = null;
        Throwable throwable = null;
        for (int i = 0; i <= count; i++) {
            try {
                // 重试间隔
                if (i > 0 && sleep > 0) {
                    Thread.sleep(sleep);
                }
                // 执行方法
                result = supplier.get();
                // 检查结果
                if (predicate == null || predicate.test(result)) {
                    return result;
                }
            } catch (Throwable t) {
                throwable = t;
            }
        }
        // 异常处理
        if (throwable != null && function != null) {
            result = function.apply(throwable);
        }
        return result;
    }

}
