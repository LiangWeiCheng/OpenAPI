package com.womai.mobile.api.service.operate.paramsopt.impl;

import com.womai.mobile.api.service.operate.paramsopt.IParamsOperate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.service.operate.params
 * 创建者: Chang Jinan
 * 日  期: 2016/5/16
 * 时  间: 17:48
 * 描  述：
 */
@Service("cmsParams")
public class CmsParams implements IParamsOperate {

    public Map<String, String> execute(Map<String, String> params) {
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("id", params.get("sid"));
        returnMap.put("appType", null);
        return returnMap;
    }
}
