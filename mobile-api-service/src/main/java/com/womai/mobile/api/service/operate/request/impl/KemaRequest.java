package com.womai.mobile.api.service.operate.request.impl;

import com.womai.mobile.api.common.constant.Constant;
import com.womai.mobile.api.common.util.GsonUtilsExt;
import com.womai.mobile.api.common.util.ResponseUtil;
import com.womai.mobile.api.domain.CommonData;
import com.womai.mobile.api.domain.HeaderInfo;
import com.womai.mobile.api.domain.ValidationInfo;
import com.womai.mobile.api.domain.response.KemaError;
import com.womai.mobile.api.domain.response.ResponseVO;
import com.womai.mobile.api.service.BaseService;
import com.womai.mobile.api.service.common.http.HttpService;
import com.womai.mobile.api.service.operate.request.IServerRequest;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.service.request
 * 创建者: Chang Jinan
 * 日  期: 2016/5/13
 * 时  间: 14:39
 * 描  述：
 */
@Service("kemaRequest")
public class KemaRequest extends BaseService implements IServerRequest {

    @Value("${mobile.app.url}")
    private String kemaUrl;
    @Autowired
    private HttpService httpService;

    @Override
    public ResponseVO httpRequest(ValidationInfo validationInfo, Map<String, String> paramsMap, String requestType, String requestPath) throws Exception {
        ResponseVO responseVO = new ResponseVO();
        //生成请求路径
        String url = kemaUrl + requestPath;
        //组建请求头
        Map<String, String> header = validationInfo.toMap();
        header.remove("headerInfo");
        header.remove("headerData");

        String result = null;
        if("GET".equals(requestType)) {
            result = httpService.doGET(url, header, paramsMap);
        }
        else {
            result = httpService.doPOST(url, header, paramsMap);
        }
        //处理服务器返回的字符串
        if(result != null && !result.equals("")) {
            KemaError error = GsonUtilsExt.fromJson(result, KemaError.class);
            if (("error").equals(error.getResponse())) {
                String msg = ResponseUtil.getMessage(Constant.KEMA_RETURN_ERROR);
                responseVO = ResponseUtil.getResponseVO(Constant.KEMA_RETURN_ERROR, msg + error.getError().text);
            }
            else {
                String msg = ResponseUtil.getMessage(Constant.SUCCESS);
                responseVO = ResponseUtil.getResponseVO(Constant.SUCCESS, msg);
                responseVO.data = result;
            }
        }else{
            String msg = ResponseUtil.getMessage(Constant.KEMA_RETURNNULL);
            responseVO = ResponseUtil.getResponseVO(Constant.KEMA_RETURNNULL, msg);
        }
        return responseVO;
    }
}
