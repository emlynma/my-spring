package com.emlynma.spring.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public abstract class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.findAndRegisterModules();
    }

    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(String json, Class<T> tClass) {
        try {
            return OBJECT_MAPPER.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            var mapType = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass);
            return OBJECT_MAPPER.readValue(json, mapType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <K, V> Map<K, V> toMap(Object object, Class<K> kClass, Class<V> vClass) {
        try {
            var mapType = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass);
            return OBJECT_MAPPER.convertValue(object, mapType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <E> List<E> toList(String json, Class<E> eClass) {
        try {
            var collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, eClass);
            return OBJECT_MAPPER.readValue(json, collectionType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <E> E[] toArray(String json, Class<E> eClass) {
        try {
            var arrayType = OBJECT_MAPPER.getTypeFactory().constructArrayType(eClass);
            return OBJECT_MAPPER.readValue(json, arrayType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T clone(Object object, Class<T> tClass) {
        return toObject(toJson(object), tClass);
    }

}