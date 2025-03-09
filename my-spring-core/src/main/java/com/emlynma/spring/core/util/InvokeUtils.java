package com.emlynma.spring.core.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
@UtilityClass
public class InvokeUtils {

    public static <T> T call(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            log.error("invoke call error, default return null.", e);
            return null;
        }
    }

    public static void run(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            log.error("invoke run error", e);
        }
    }

}
