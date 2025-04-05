package com.emlynma.spring.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

@UtilityClass
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper()
                .findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        ;
    }

    @SneakyThrows
    public static String toJson(Object object) {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    @SneakyThrows
    public static <T> T toObject(String json, Class<T> type) {
        return OBJECT_MAPPER.readValue(json, type);
    }

    @SneakyThrows
    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        return OBJECT_MAPPER.readValue(json, typeReference);
    }

    @SneakyThrows
    public static Map<String, Object> toMap(String json) {
        var mapType = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        return OBJECT_MAPPER.readValue(json, mapType);
    }

    @SneakyThrows
    public static <K, V> Map<K, V> toMap(Object object) {
        var mapType = OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        return OBJECT_MAPPER.convertValue(object, mapType);
    }

    @SneakyThrows
    public static <E> List<E> toList(String json, Class<E> type) {
        var collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, type);
        return OBJECT_MAPPER.readValue(json, collectionType);
    }

    @SneakyThrows
    public static <E> E[] toArray(String json, Class<E> type) {
        var arrayType = OBJECT_MAPPER.getTypeFactory().constructArrayType(type);
        return OBJECT_MAPPER.readValue(json, arrayType);
    }

    @SneakyThrows
    public static String toPrettyJson(Object object) {
        return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

}