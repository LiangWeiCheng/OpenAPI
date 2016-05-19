package com.womai.mobile.api.service.operate.cacheget;

import com.womai.mobile.api.domain.ValidationInfo;

import java.util.Map;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.service.operate.params
 * 创建者: Chang Jinan
 * 日  期: 2016/5/16
 * 时  间: 17:52
 * 描  述：缓存存放接口
 */
public interface ICacheGet {

    public String execute(ValidationInfo validationInfo, Map<String, String> paramsMap);
}
