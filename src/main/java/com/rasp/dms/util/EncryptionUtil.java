package com.rasp.dms.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class EncryptionUtil {

    @Value("${encryption.algorithm}")
    private String algorithm;

    @Value("${encryption.transformation}")
    private String transformation;

    @Value("${encryption.key-length}")
    private int keyLength;

    public SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(keyLength);
        return keyGenerator.generateKey();
    }

    public String encodeKey(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public SecretKey decodeKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, algorithm);
    }

    public byte[] encrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);

        // Generate random IV
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encryptedData = cipher.doFinal(data);

        // Combine IV and encrypted data
        byte[] encryptedWithIv = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
        System.arraycopy(encryptedData, 0, encryptedWithIv, iv.length, encryptedData.length);

        return encryptedWithIv;
    }

    public byte[] decrypt(byte[] encryptedWithIv, SecretKey key) throws Exception {
        // Extract IV and encrypted data
        byte[] iv = new byte[16];
        byte[] encryptedData = new byte[encryptedWithIv.length - 16];

        System.arraycopy(encryptedWithIv, 0, iv, 0, 16);
        System.arraycopy(encryptedWithIv, 16, encryptedData, 0, encryptedData.length);

        Cipher cipher = Cipher.getInstance(transformation);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        return cipher.doFinal(encryptedData);
    }

    public String generateKeyId() {
        return "key_" + System.currentTimeMillis() + "_" + new SecureRandom().nextInt(1000);
    }
}

