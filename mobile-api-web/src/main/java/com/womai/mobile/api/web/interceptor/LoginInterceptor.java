package com.womai.mobile.api.web.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.womai.mobile.api.common.util.GsonUtilsExt;
import com.womai.mobile.api.domain.CommonData;
import com.womai.mobile.api.domain.HeaderInfo;
import com.womai.mobile.api.domain.UserValidation;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.web.interceptor
 * 创建者: Chang Jinan
 * 日  期: 2016/5/9
 * 时  间: 13:38
 * 描  述：
 */
public class LoginInterceptor extends BaseInterceptor implements HandlerInterceptor {

    /**
     * 加入请求之前的操作
     **/
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        if(handleHttpServletRequest(httpServletRequest, httpServletResponse)) {

            if(handleThreeDES(httpServletRequest, httpServletResponse)) {
                HeaderInfo headerInfo = (HeaderInfo) httpServletRequest.getAttribute("headerInfo");
                String data = httpServletRequest.getParameter("data");
                //组装网站后台需要的header头部信息
                JsonNode jsonNode = GsonUtilsExt.toJsonNode(data);
                httpServletRequest.setAttribute("jsonData", jsonNode.findValue("data"));
                CommonData common = GsonUtilsExt.fromJson(jsonNode.findValue("common").toString(), CommonData.class);

                UserValidation userValidation = new UserValidation();
                handleValidationInfo(headerInfo, common, userValidation);
                userValidation.setUsersession(common.getUserSession());
                userValidation.setUserId(common.getUserId());
                httpServletRequest.setAttribute("validationInfo", userValidation);
                return true;
            }
            return false;
        }
        return false;
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
