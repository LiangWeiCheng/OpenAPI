package com.womai.mobile.api.service.operate.response;

import com.womai.mobile.api.domain.response.ResponseVO;

import java.util.Map;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.service.operate.response
 * 创建者: Chang Jinan
 * 日  期: 2016/5/19
 * 时  间: 15:20
 * 描  述：返回数据处理
 */
public interface IServerResponse {

    /**
     * 处理服务器返回的相应数据
     * @param responseVO
     * @return
     * @throws Exception
     */
    public ResponseVO execute(ResponseVO responseVO) throws Exception;

}
