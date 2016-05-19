package com.womai.mobile.api.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.womai.mobile.api.domain.HeaderInfo;
import com.womai.mobile.api.domain.ValidationInfo;
import com.womai.mobile.api.domain.response.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2015/10/28.
 */
public class BaseController {

    /**
     * 获取request中的attribute:jsonData
     *
     * @param request
     * @return
     */
    public JsonNode getJsonData(HttpServletRequest request) {
        return (JsonNode) request.getAttribute("jsonData");
    }

    /**
     * 获取request中的attribute:headerInfo
     *
     * @param request
     * @return
     */
    public Object getHeaderInfo(HttpServletRequest request) {
        return request.getAttribute("headerInfo");
    }

    /**
     * 获取request中的attribute:validationInfo
     *
     * @param request
     * @return
     */
    public Object getValidationInfo(HttpServletRequest request) {
        return  request.getAttribute("validationInfo");
    }

    public ResponseVO execute(HttpServletRequest request) throws Exception {
        return null;
    }
}
