package com.emlynma.spring.core.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class CopyUtils {

    @SneakyThrows
    public static void copyNonNullProperties(Object source, Object target, Class<?> clazz) {
        if (!clazz.isInstance(source) || !clazz.isInstance(target)) {
            throw new ClassCastException("class not match");
        }
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(source);
            if (value != null) {
                field.set(target, value);
            }
        }
    }

    @SneakyThrows
    public static <T> T copyShallowly(T source, Class<T> tClass) {
        T copy = tClass.getDeclaredConstructor().newInstance();
        for (Field field : tClass.getDeclaredFields()) {
            field.setAccessible(true);
            field.set(copy, field.get(source));
        }
        return copy;
    }

    public static <T> T copyDeeply(T source, Class<T> tClass) {
        return JsonUtils.clone(source, tClass);
    }

}
