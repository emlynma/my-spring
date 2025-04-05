package com.emlynma.spring.core.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.security.MessageDigest;

@UtilityClass
public class HashUtils {

    private static final String MD5_ALGORITHM = "MD5";
    private static final String SHA_ALGORITHM = "SHA-256";

    public static String encryptMD5(String content) {
        return generateHash(content, MD5_ALGORITHM);
    }

    public static String encryptSHA(String content) {
        return generateHash(content, SHA_ALGORITHM);
    }

    @SneakyThrows
    private static String generateHash(String content, String algorithm) {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        var bytes = digest.digest(content.getBytes());
        return bytesToHex(bytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

}
