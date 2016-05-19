package com.womai.mobile.api.service.operate.paramsopt;

import java.util.Map;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.service.operate.params
 * 创建者: Chang Jinan
 * 日  期: 2016/5/16
 * 时  间: 17:52
 * 描  述：参数处理接口
 */
public interface IParamsOperate {
    public Map<String, String> execute(Map<String, String> params);
}
