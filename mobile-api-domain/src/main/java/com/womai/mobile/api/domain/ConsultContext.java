package com.womai.mobile.api.domain;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.domain
 * 创建者: Chang Jinan
 * 日  期: 2016/5/11
 * 时  间: 16:47
 * 描  述：
 */
public class ConsultContext {
    /**
     * RSA公钥
     */
    private String public_key = "";

    /**
     * 模数值
     */
    private String modulus_key = "";

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getModulus_key() {
        return modulus_key;
    }

    public void setModulus_key(String modulus_key) {
        this.modulus_key = modulus_key;
    }
}
