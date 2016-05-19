package com.womai.mobile.api.service.operate.request;

import com.womai.mobile.api.domain.HeaderInfo;
import com.womai.mobile.api.domain.ValidationInfo;
import com.womai.mobile.api.domain.response.ResponseVO;

import java.util.Map;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.service.request
 * 创建者: Chang Jinan
 * 日  期: 2016/5/13
 * 时  间: 14:40
 * 描  述：
 */
public interface IServerRequest {

    /**
     * 发送POST请求到服务端
     * @param validationInfo 校验信息
     * @param paramsMap 请求参数
     * @param requestType 请求类型
     * @param requestPath 请求路径
     * @return
     */
    public ResponseVO httpRequest(ValidationInfo validationInfo, Map<String, String> paramsMap, String requestType, String requestPath) throws Exception;

}
