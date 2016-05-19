package com.womai.mobile.api.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {

    public static final String PRIVATE_KEY = "privateKey";
    public static final String PUBLIC_KEY = "publicKey";
    public static final String MODULUS = "modulus";

    private static final String RSA = "RSA";
    /**
     * 指定加密算法为DESede
     */
    private static String ALGORITHM = "RSA/ECB/PKCS1Padding";
    /**
     * 指定key的大小
     */
    private static int KEYSIZE = 1024;
    /**
     * 默认编码
     */
    private static final String UTF8 = "UTF-8";

    public static void main(String[] args) throws Exception {
        //用来加解密随机对称密钥
        String cryptograph = "UFYzbTRSRHdFUUNJR3hWdjAAVmJsRkJ0bmlNTG5PUS9vanhlaFNKR0pBTTdMMlR4dGdsa3pvL3I1V0NHc0x2amwwMWdGUlJ0TnMvL0t0T1NwRzRWUStSVGRtQ2ZudDdGenFKbGNFTHRGeWNwbzRyZnZ0b2QreE9EZUU3RXZlblVMZGZWY3BCSmJTMDBmN0kyM2JPZ3Nkelc2aTFFZ1YyYm9GMU5HYThlbi9vPQ==";
        byte[] b1 = Base64.decodeBase64(cryptograph.getBytes());
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAILtlOhJZGzysUb3DyY6If4wjOD1TJvyY5MaAHfTgKlxD44jhJLVT5WTTrs466q5YRrKGdcelFWSNO+s0h9YCubhBaPxlS49T9vhxV9smoucPNNPqnyBYJ4fyrRxnzGHAc4hAlKbfdt9rvvPLiTpvQxA0fDX0xN1Fm31+/Mx2nytAgMBAAECgYBZc0Yj+ViCUsEpccvI2zi0OKlXXGANv2mma8JcG4UwvozZGI0P4GqyD2Vf5kCkcGjWa7OB1GOVnwFJqnIYdgdjvh1WoXwH3t6CSn/fd7AQaln9VBXSVzryqYNne6TMJW1hTRjwJLIOGtSFdJiTX76DslkE22ixYwNHH9AG1QSGoQJBANjvkjBsJgX/2T3We4WlR50g0EACjMysLHLhX0iDRMQcqvwVla7DLQvtZsfxqzU0Be09XxrkCHSr+xjExdRfHTkCQQCagS0hfYF6hPEZXoNBqDXpeOCFfglHpHECc0LV9sSm5MF5v3RGXGg3ngd8WlOXN3fWGbveHt14Jbw68m00zM8VAkEAkaEJ912yjNZ7JKsGUupoT9AGXOIZWRC/6gLnbwtIL4q7MezfhelaJSail9jhQR8CV4eaf7fTAVeugasezvlm6QJAaSSVSOA3F+CWjnQiELFfFuY6rL2rpQSEH22+wwruosCz5Z1jjGpgRdEWOQrjLdoChEH49ly8zIGpidfmd3MVTQJAMSMh3HepPBOqiHKX/mVoKNdZn/ft513iS2IsJcOqBVQLh2m1vikePis9kwkjiCEEzijz+BuuRyMPBWAaz3SZvw==";

        System.out.println(RSAUtil.decrypt(new String(b1), privateKey));
    }

    /**
     * 生成密钥对
     */
    public static Map<String, String> generateKeyPair() throws Exception {
        /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        kpg.initialize(KEYSIZE, sr);
        /** 生成密匙对 */
        KeyPair kp = kpg.generateKeyPair();
        /** 得到公钥 */
        Key publicKey = kp.getPublic();
        byte[] publicKeyBytes = publicKey.getEncoded();
        String pub = new String(Base64.encodeBase64(publicKeyBytes), UTF8);
        /** 得到私钥 */
        Key privateKey = kp.getPrivate();
        byte[] privateKeyBytes = privateKey.getEncoded();
        String pri = new String(Base64.encodeBase64(privateKeyBytes), UTF8);

        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put(PUBLIC_KEY, pub);
        returnMap.put(PRIVATE_KEY, pri);

        RSAPublicKey rsp = (RSAPublicKey) kp.getPublic();
        BigInteger bint = rsp.getModulus();

        byte[] b = bint.toByteArray();
        byte[] deBase64Value = Base64.encodeBase64(b);

        String retValue = new String(deBase64Value);
        returnMap.put(MODULUS, retValue);

        return returnMap;
    }

    /**
     * 加密方法 source： 源数据
     */
    public static String encrypt(String source, String publicKey)
            throws Exception {
        Key key = getPublicKey(publicKey);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] b = source.getBytes();
        /** 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);

        return new String(Base64.encodeBase64(b1), UTF8);
    }

    /**
     * 解密算法 cryptograph:密文
     */
    public static String decrypt(String cryptograph, String privateKey)
            throws Exception {
        Key key = getPrivateKey(privateKey);
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] b1 = Base64.decodeBase64(cryptograph.getBytes());
        /** 执行解密操作 */
        byte[] b = cipher.doFinal(b1);

        return new String(b);
    }

    /**
     * 得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                Base64.decodeBase64(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        return publicKey;
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                Base64.decodeBase64(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        return privateKey;
    }
}
