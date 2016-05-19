package com.womai.mobile.api.service.common.rsaSession;

/**
 * Created with IntelliJ IDEA.
 * User: jijun.yang
 * Date: 13-12-6
 * Time: 下午4:44
 * To change this template use File | Settings | File Templates.
 */
public interface RsaSessionCacheService {
    /**
     * 保存rsa协商私钥
     * @param key
     * @param value
     */
    public String setRsaSessionKey(String key, String value);

    /**
     * 取rsa协商私钥
     * @param key
     * @return
     */
    public String getRsaSessionKey(String key);
}
