package com.emlynma.spring.core.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
@UtilityClass
public class InvokeUtils {

    /**
     * 吞没异常调用
     */
    public static <T> T callQuietly(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            log.error("invoke call error, default return null.", e);
            return null;
        }
    }

    /**
     * 吞没异常运行
     */
    public static void runQuietly(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            log.error("invoke run error", e);
        }
    }

    /**
     * 可重试调用
     */
    public static <V> V callRetryable(Callable<V> callable, int count) {
        Exception exception = null;
        for (int i = 0; i <= count; i++) {
            try {
                return callable.call();
            } catch (Exception e) {
                exception = e;
            }
        }
        throw new RuntimeException(exception);
    }

    /**
     * 可重试运行
     */
    public static <V> V runRetryable(Runnable runnable, int count) {
        Exception exception = null;
        for (int i = 0; i <= count; i++) {
            try {
                runnable.run();
            } catch (Exception e) {
                exception = e;
            }
        }
        throw new RuntimeException(exception);
    }

    /**
     * 忽略中断休眠
     */
    private static void sleep(long millis) {
        long endtime = System.currentTimeMillis() + millis;
        while (millis > 0) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                // 忽略中断
                log.warn("sleep interrupted", e);
            }
            millis = endtime - System.currentTimeMillis();
        }
    }

}
