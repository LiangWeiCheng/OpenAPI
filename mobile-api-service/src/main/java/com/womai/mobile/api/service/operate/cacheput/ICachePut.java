package com.womai.mobile.api.service.operate.cacheput;

import java.util.Map;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.service.operate.params
 * 创建者: Chang Jinan
 * 日  期: 2016/5/16
 * 时  间: 17:52
 * 描  述：缓存获取接口
 */
public interface ICachePut {

    public boolean execute(Map<String, String> params);
}
