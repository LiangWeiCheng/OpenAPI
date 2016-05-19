package com.womai.mobile.api.web.acpect;

import com.womai.mobile.api.common.constant.Constant;
import com.womai.mobile.api.domain.response.ResponseVO;
import com.womai.mobile.api.service.common.threeDES.ThreeDESService;
import com.womai.mobile.api.web.entrance.ApiEntrance;
import org.apache.commons.codec.binary.Base64;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.web.acpect
 * 创建者: Chang Jinan
 * 日  期: 2016/5/17
 * 时  间: 11:29
 * 描  述：
 */
@Component
@Aspect
public class EntranceAspect extends BaseAspect {

    @Autowired
    private ThreeDESService threeDESService;

    //配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
    @Pointcut("execution(* com.womai.mobile.api.web.entrance.ApiEntrance.*(..))")
    public void aspect(){	}

    //配置后置返回通知,使用在方法aspect()上注册的切入点
    @AfterReturning(value = "aspect()")
    public void afterReturn(JoinPoint joinPoint){
        ApiEntrance entrance = (ApiEntrance) joinPoint.getTarget();
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        Object encript = request.getAttribute("encript");
        if(encript != null) {
            ResponseVO responseVO = entrance.getResponseVO();
            if("00".equals(responseVO.code)) {
                String result = responseVO.data;
                try {
                    if(Boolean.parseBoolean(encript.toString())) {
                        String originalKey = request.getAttribute("originalKey").toString();
                        responseVO.data = threeDESService.encryptMode(originalKey, result);
                    }
                    else {
                        responseVO.data = new String(Base64.encodeBase64(result.getBytes("UTF-8")), "UTF-8");
                    }
                } catch (UnsupportedEncodingException e) {
                    responseVO.code = Constant.UNKNOW_ERROR;
                    responseVO.message = "对返回字符串进行加密/编码错误！";
                    responseVO.data = "";
                }
            }
        }
    }
}
