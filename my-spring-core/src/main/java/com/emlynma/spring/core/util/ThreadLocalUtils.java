package com.emlynma.spring.core.util;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ThreadLocalUtils {

    private static final ThreadLocal<Map<String, Object>> LOCAL_MAP = ThreadLocal.withInitial(HashMap::new);

    public static void put(String key, Object value) {
        LOCAL_MAP.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) LOCAL_MAP.get().get(key);
    }

    public static void remove(String key) {
        LOCAL_MAP.get().remove(key);
    }

    public static void clear() {
        LOCAL_MAP.remove();
    }

}
