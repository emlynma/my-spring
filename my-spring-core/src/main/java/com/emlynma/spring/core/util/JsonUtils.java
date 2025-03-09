package com.emlynma.spring.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

@UtilityClass
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.findAndRegisterModules();
    }

    @SneakyThrows
    public static String toJson(Object object) {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    @SneakyThrows
    public static <T> T toObject(String json, Class<T> tClass) {
        return OBJECT_MAPPER.readValue(json, tClass);
    }

    @SneakyThrows
    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        return OBJECT_MAPPER.readValue(json, typeReference);
    }

    @SneakyThrows
    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        var mapType = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass);
        return OBJECT_MAPPER.readValue(json, mapType);
    }

    @SneakyThrows
    public static <K, V> Map<K, V> toMap(Object object, Class<K> kClass, Class<V> vClass) {
        var mapType = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass);
        return OBJECT_MAPPER.convertValue(object, mapType);
    }

    @SneakyThrows
    public static <E> List<E> toList(String json, Class<E> eClass) {
        var collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, eClass);
        return OBJECT_MAPPER.readValue(json, collectionType);
    }

    @SneakyThrows
    public static <E> E[] toArray(String json, Class<E> eClass) {
        var arrayType = OBJECT_MAPPER.getTypeFactory().constructArrayType(eClass);
        return OBJECT_MAPPER.readValue(json, arrayType);
    }

    public static <T> T clone(Object object, Class<T> tClass) {
        return toObject(toJson(object), tClass);
    }

}