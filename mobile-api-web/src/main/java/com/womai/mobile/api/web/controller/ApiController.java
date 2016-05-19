package com.womai.mobile.api.web.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.womai.mobile.api.common.util.Util;
import com.womai.mobile.api.common.util.prop.ReqParamsProp;
import com.womai.mobile.api.domain.HeaderInfo;
import com.womai.mobile.api.domain.ValidationInfo;
import com.womai.mobile.api.domain.response.ResponseVO;
import com.womai.mobile.api.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.web.controller
 * 创建者: Chang Jinan
 * 日  期: 2016/5/12
 * 时  间: 14:56
 * 描  述：
 */
@Component("apiController")
public class ApiController extends BaseController {

    @Autowired
    private WorkFlowService workFlowService;

    private ResponseVO responseVO;

    @Override
    public ResponseVO execute(HttpServletRequest request) throws Exception {

        String requestURI = request.getRequestURI();
        ValidationInfo validationInfo = (ValidationInfo) getValidationInfo(request);
        JsonNode jsonNode = getJsonData(request);
        String paramsStr = ReqParamsProp.getMapValue(requestURI, "/controller/api-url-params.properties");
        Map<String, String> paramsMap = new HashMap<String, String>();
        if(paramsStr != null) {
            String[] params = paramsStr.split(",");
            for(String param : params) {
                paramsMap.put(param, Util.replaceNull(jsonNode.findValue(param)));
            }
        }
        responseVO = workFlowService.execute(validationInfo, paramsMap, requestURI);
        return responseVO;
    }
}
