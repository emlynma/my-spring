package com.emlynma.spring.core.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class SignUtils {

    private static final String HASH_ALGORITHM = "RSA";
    private static final String SIGN_ALGORITHM = "SHA256withRSA";
    private static final String SIGN_FIELD = "sign";

    public static String sign(Object object, String privateKey) {
        return sign(buildSignString(object), privateKey);
    }

    public static boolean verify(Object object, String sign, String publicKey) {
        return verify(buildSignString(object), sign, publicKey);
    }

    @SneakyThrows
    private static String sign(String content, String privateKey) {
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initSign(KeyFactory.getInstance(HASH_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey))));
        signature.update(content.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    @SneakyThrows
    private static boolean verify(String content, String sign, String publicKey) {
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initVerify(KeyFactory.getInstance(HASH_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey))));
        signature.update(content.getBytes(StandardCharsets.UTF_8));
        return signature.verify(Base64.getDecoder().decode(sign));
    }

    private static String buildSignString(Object object) {
        if (isPrimitiveType(object)) {
            return String.valueOf(object);
        }
        Map<String, Object> fieldMap = JsonUtils.toMap(object);
        return fieldMap.entrySet().stream()
                .filter(entry -> Objects.nonNull(entry.getValue()))
                .filter(entry -> isPrimitiveType(entry.getValue()))
                .filter(entry -> !String.valueOf(entry.getValue()).isEmpty())
                .filter(entry -> !entry.getKey().equals(SIGN_FIELD))
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

    private static boolean isPrimitiveType(Object object) {
        return object instanceof String || object instanceof Number || object instanceof Boolean;
    }

}
