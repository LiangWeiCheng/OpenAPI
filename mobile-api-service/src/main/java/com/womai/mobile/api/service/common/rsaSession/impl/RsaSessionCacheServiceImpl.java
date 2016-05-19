package com.womai.mobile.api.service.common.rsaSession.impl;

import com.womai.mobile.api.service.BaseService;
import com.womai.mobile.api.service.common.redis.RedisObjectService;
import com.womai.mobile.api.service.common.rsaSession.RsaSessionCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: jijun.yang
 * Date: 13-12-6
 * Time: 下午4:49
 * To change this template use File | Settings | File Templates.
 */
@Service("rsaSessionCacheService")
public class RsaSessionCacheServiceImpl extends BaseService implements RsaSessionCacheService {

    @Autowired
    private RedisObjectService redisObjectService;
    @Value("${RSASESSION.KEY}")
    private String rsaSessionKey;
    @Value("${RSASESSION.TIME}")
    private String rsaSessionTime;

    @Override
    //final String key, final String value, final int expire
    public String setRsaSessionKey(String key, String value) {
       return  redisObjectService.setString(rsaSessionKey + "." + key, value, Integer.parseInt(rsaSessionTime));
    }

    @Override
    public String getRsaSessionKey(String key) {
        return redisObjectService.getString(rsaSessionKey + "." + key);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
