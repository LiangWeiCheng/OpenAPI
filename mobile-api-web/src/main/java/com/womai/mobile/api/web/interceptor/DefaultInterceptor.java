package com.womai.mobile.api.web.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.womai.mobile.api.common.util.GsonUtilsExt;
import com.womai.mobile.api.common.util.ThreeDES;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.web.interceptor
 * 创建者: Chang Jinan
 * 日  期: 2016/5/11
 * 时  间: 10:54
 * 描  述：
 */
public class DefaultInterceptor extends BaseInterceptor implements HandlerInterceptor {

    /**
     * 固定密钥
     */
    @Value("${3DES.key}")
    private String originalKey;

    /**
     * 加入请求之前的操作
     **/
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String data = httpServletRequest.getParameter("data");
        JsonNode jsonNode = GsonUtilsExt.toJsonNode(ThreeDES.orginalDecryptMode(originalKey, data));
        httpServletRequest.setAttribute("jsonData", jsonNode.findValue("data"));
        return handleHttpServletRequest(httpServletRequest, httpServletResponse);
    }

    /**
     * 加入生成视图之前的处理，
     * 但不能处理到@ResponseBody的数据，因ResponseBody已先返回到客户端
     **/
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在DispatcherServlet完全处理完请求(返回视图给客户端)后被调用,可用于清理资源等
     **/
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
