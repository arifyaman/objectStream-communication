package com.xlip.objectstream.communication.service;

import com.xlip.objectstream.communication.LockedWrap;
import com.xlip.objectstream.communication.Wrap;
import lombok.Getter;
import lombok.Setter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class EncryptionService {
    @Getter
    public static EncryptionService instance = new EncryptionService();

    @Getter
    @Setter
    private String iv;

    @Getter
    @Setter
    private String secretKey;

    @Getter
    private boolean initialized = false;

    public Wrap test() {
        return Wrap.builder().cmd("asdasd").success(true).message("asdasdasd").build();
    }

    public static EncryptionService init(String iv, String secretKey) {
        instance.setIv(iv);
        instance.setSecretKey(secretKey);
        instance.initialized = true;
        return instance;
    }


    public LockedWrap lockWrap(Wrap wrap) {
        byte[] encrypted = new byte[0];

        try {
            IvParameterSpec iv = new IvParameterSpec(this.iv.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(this.secretKey.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(wrap);
            out.flush();
            byte[] yourBytes = bos.toByteArray();
            encrypted = cipher.doFinal(yourBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LockedWrap(encrypted);
    }

    public Wrap resolveWrap(byte[] encrypted) throws IllegalBlockSizeException, BadPaddingException {
        IvParameterSpec iv = new IvParameterSpec(this.iv.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec skeySpec = new SecretKeySpec(this.secretKey.getBytes(StandardCharsets.UTF_8), "AES");

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] orginal = cipher.doFinal(encrypted);
            ByteArrayInputStream bis = new ByteArrayInputStream(orginal);
            ObjectInput in = new ObjectInputStream(bis);
            return ((Wrap) in.readObject());
        } catch (Exception ignore) {
        }


        return null;
    }
}
