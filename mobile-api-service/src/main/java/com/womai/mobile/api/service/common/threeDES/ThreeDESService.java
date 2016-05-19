package com.womai.mobile.api.service.common.threeDES;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2015/10/23.
 */
public interface ThreeDESService {
    /**
     * 对称密钥解密
     *
     * @param key 对称密钥
     * @param src 密文
     * @return
     */
    public String encryptMode(String key, Object src) throws UnsupportedEncodingException;

    /**
     * 对称密钥解密
     *
     * @param key 对称密钥
     * @param src 密文
     * @return
     */
    public String decryptMode(String key, String src) throws Exception;
}
