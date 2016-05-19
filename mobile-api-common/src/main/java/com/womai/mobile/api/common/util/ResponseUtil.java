package com.womai.mobile.api.common.util;

import com.womai.mobile.api.common.constant.Constant;
import com.womai.mobile.api.common.exception.MobileApiException;
import com.womai.mobile.api.common.util.prop.ExceptionProp;
import com.womai.mobile.api.domain.response.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ResponseUtil {
    static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    public static String exceptionCode(Exception e) {
        if (e instanceof MobileApiException || e instanceof IOException) {
            logger.error("前置机连接服务器异常：" + e.getMessage());
            return Constant.FORNT_LINK_SERVER_ERROR;
        } else {
            return Constant.FORNT_ERROR;
        }
    }

    /**
     * 根据code取msg
     *
     * @param code
     */
    public static String getMessage(String code){
        try{
            String msg = ExceptionProp.getMapValue(code, "/exception/code-message.properties");
            return msg;
        }catch(Exception e){
            logger.error("", e);
        }
        return "获取不到错误代码信息";
    }

    /**
     * 返回结果转化
     *
     * @param code:    异常code
     * @param msg：异常信息
     * @return
     * @throws Exception
     */
    public static ResponseVO getResponseVO(String code, String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.code = code;
        responseVO.message = msg;
        responseVO.data = "";
        return responseVO;
    }
}
