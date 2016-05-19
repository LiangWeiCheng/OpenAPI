package com.womai.mobile.api.service.operate.request.impl;

import com.womai.mobile.api.common.constant.Constant;
import com.womai.mobile.api.common.util.GsonUtilsExt;
import com.womai.mobile.api.common.util.ResponseUtil;
import com.womai.mobile.api.domain.ValidationInfo;
import com.womai.mobile.api.domain.response.ResponseVO;
import com.womai.mobile.api.domain.response.WxPayError;
import com.womai.mobile.api.service.BaseService;
import com.womai.mobile.api.service.common.http.HttpService;
import com.womai.mobile.api.service.operate.request.IServerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.service.operate.request.impl
 * 创建者: Chang Jinan
 * 日  期: 2016/5/18
 * 时  间: 10:42
 * 描  述：
 */
@Service("wxPayRequest")
public class WxPayRequest extends BaseService implements IServerRequest {

    @Value("${wx.url}")
    private String wxUrl;
    @Autowired
    private HttpService httpService;

    @Override
    public ResponseVO httpRequest(ValidationInfo validationInfo, Map<String, String> paramsMap, String requestType, String requestPath) throws Exception {
        ResponseVO responseVO = new ResponseVO();
        //生成请求路径
        String url = wxUrl + requestPath;
        //组建请求头
        Map<String, String> header = validationInfo.toMap();
        header.put("headerData", validationInfo.getHeaderInfo());
        header.remove("headerInfo");

        String result = null;
        if("GET".equals(requestType)) {
            result = httpService.doGET(url, header, paramsMap);
        }
        else {
            result = httpService.doPOST(url, header, paramsMap);
        }
        //处理服务器返回的字符串
        if(result != null && !result.equals("")) {
            WxPayError error = GsonUtilsExt.fromJson(result, WxPayError.class);
            if (("0").equals(error.getErrcode())) {
                String msg = ResponseUtil.getMessage(Constant.SUCCESS);
                responseVO = ResponseUtil.getResponseVO(Constant.SUCCESS, msg);
                responseVO.data = result;
            }
            else {
                String msg = ResponseUtil.getMessage(Constant.WXPAY_RETURN_ERROR);
                responseVO = ResponseUtil.getResponseVO(Constant.WXPAY_RETURN_ERROR, msg + error.getErrmsg());
            }
        }else{
            String msg = ResponseUtil.getMessage(Constant.WXPAY_RETURNNULL);
            responseVO = ResponseUtil.getResponseVO(Constant.WXPAY_RETURNNULL, msg);
        }
        return responseVO;
    }
}
