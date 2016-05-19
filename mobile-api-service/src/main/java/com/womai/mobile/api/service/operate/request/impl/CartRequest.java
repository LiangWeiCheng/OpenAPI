package com.womai.mobile.api.service.operate.request.impl;

import com.womai.mobile.api.common.constant.Constant;
import com.womai.mobile.api.common.util.GsonUtilsExt;
import com.womai.mobile.api.common.util.ResponseUtil;
import com.womai.mobile.api.domain.CommonData;
import com.womai.mobile.api.domain.HeaderInfo;
import com.womai.mobile.api.domain.ValidationInfo;
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
@Service("cartRequest")
public class CartRequest extends BaseService implements IServerRequest {

    @Value("${mobile.cache.url}")
    private String cartUrl;
    @Autowired
    private HttpService httpService;

    @Override
    public ResponseVO httpRequest(ValidationInfo validationInfo, Map<String, String> paramsMap, String requestType, String requestPath) throws Exception {

        ResponseVO responseVO = new ResponseVO();
        //生成请求路径
        String url = cartUrl + requestPath;
        //组建请求头
        Map<String, String> header = new HashMap<String, String>();
        header.put("headerData", validationInfo.getHeaderData());

        //组建请求参数
        Map<String, Object> commData = new HashMap<String, Object>();
        CommonData c = new CommonData();
        c.setMid(validationInfo.getMid());
        c.setUserId(validationInfo.getUserId());
        c.setUserSession("");
        c.setTest1(validationInfo.getTest());
        c.setCityCode(validationInfo.getCityCode());
        commData.put("data", paramsMap);
        commData.put("common", c);
        String data = GsonUtilsExt.toJson(commData);
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("data", new String(Base64.encodeBase64(data.getBytes()), "UTF-8"));

        String result = null;
        if("GET".equals(requestType)) {
            result = httpService.doGET(url, header, dataMap);
        }
        else {
            result = httpService.doPOST(url, header, dataMap);
        }
        //处理服务器返回的字符串
        if(result != null && !result.equals("")) {
            responseVO = GsonUtilsExt.fromJson(result, ResponseVO.class);
            if (("00").equals(responseVO.code)) {
                String resData = responseVO.data;
                responseVO.data = new String(Base64.decodeBase64(resData.getBytes()), "UTF-8");
            }
        }else{
            String msg = ResponseUtil.getMessage(Constant.CART_RETURNNULL);
            responseVO = ResponseUtil.getResponseVO(Constant.CART_RETURNNULL, msg);
        }
        return responseVO;
    }
}
