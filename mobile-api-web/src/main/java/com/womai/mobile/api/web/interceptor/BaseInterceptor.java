package com.womai.mobile.api.web.interceptor;

import com.womai.common.framework.web.util.RequestUtils;
import com.womai.common.utils.GsonUtils;
import com.womai.mobile.api.common.constant.Constant;
import com.womai.mobile.api.common.util.RSAUtil;
import com.womai.mobile.api.common.util.Util;
import com.womai.mobile.api.domain.CommonData;
import com.womai.mobile.api.domain.HeaderInfo;
import com.womai.mobile.api.domain.ValidationInfo;
import com.womai.mobile.api.domain.response.ResponseVO;
import com.womai.mobile.api.service.common.rsaSession.RsaSessionCacheService;
import com.womai.mobile.api.service.common.threeDES.ThreeDESService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by Administrator on 2015/10/23.
 */
public class BaseInterceptor {

    protected transient final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ThreeDESService threeDESService;
    @Autowired
    private RsaSessionCacheService rsaSessionCacheService;

    @Value("${3DES.key}")
    private String originalKey;//默认值"qwertyuiopasdfghjkl;'zxc"
    @Value("${KEY.Test}")
    private String womaikey;
    @Value("${OS}")
    private String conf_os;
    @Value("${FILTER.OS}")
    private String filterOs;

    protected ResponseVO responseVO = new ResponseVO();

    protected void writeJson(HttpServletResponse response, String msg) {
        response.setCharacterEncoding("utf-8");
        response.setHeader("accept", "text/html");
        response.setContentType("text/html;charset=utf-8");

        Writer writer = null;
        try {
            writer = response.getWriter();
            writer.write(msg);
            writer.flush();
        } catch (IOException e) {
            logger.error("writeJson write error,msg:" + e.getMessage(), e);
        }
        finally {
            try {
                writer.close();
            } catch (IOException e) {
                logger.error("writeJson close error,msg:" + e.getMessage(), e);
            }
        }
    }

    protected String getDecryptHead(String headerData) throws Exception {
        return threeDESService.decryptMode(originalKey, headerData);
    }

    protected boolean handleHttpServletRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        /*获取头信息并解密*/
        String headerData = httpServletRequest.getHeader("headerData");
        String head = null;
        try {
            head = getDecryptHead(headerData);
        } catch (Exception e) {
            logger.info("getDecryptHead null,msg:" + e.getMessage(), e);
        }

        HeaderInfo headerInfo = GsonUtils.fromJson(head, HeaderInfo.class);
//            httpServletRequest.setAttribute("head", headerData);

        if (headerInfo == null) {
            headerInfo = GsonUtils.fromJson(headerData, HeaderInfo.class);
            headerInfo.setOs_num(headerInfo.getOs());
            httpServletRequest.setAttribute("headerInfo", headerInfo);
        }
        else {
            headerInfo.setOs_num(headerInfo.getOs());
            httpServletRequest.setAttribute("headerInfo", headerInfo);
        }
        String remoteIP = RequestUtils.getRemoteIP(httpServletRequest);
        headerInfo.setUserIp(remoteIP);
        /**
         * 判断是否为我买app的链接
         */
        if (!womaikey.equals(headerInfo.getTest())) {
            responseVO.code = Constant.UNKNOW_ERROR;
            responseVO.message = "参数错误:headerInfo.test不等于womaikey";
            responseVO.data = "";
            String result = GsonUtils.toJson(responseVO);
            writeJson(httpServletResponse, result);
            return false;
        }
        String os = Util.configToMap(conf_os, headerInfo.getOs());
        if (os != null) {
            headerInfo.setOs(os);
        } else {
            responseVO.code = Constant.UNKNOW_ERROR;
            responseVO.message = "参数错误:os为null";
            responseVO.data = "";
            String result = GsonUtils.toJson(responseVO);
            writeJson(httpServletResponse, result);
            return false;
        }
        headerInfo.setData(headerData);
        return true;
    }

    protected boolean handleThreeDES(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    //取得headerinfo
        HeaderInfo headerInfo = (HeaderInfo) httpServletRequest.getAttribute("headerInfo");
        //商品详情(skudetail/index.action)在2.6.0之后的版本是用户相关接口，需要解密，按照UserValidation处理, 但不登录的用户也能拿到数据

        String key = httpServletRequest.getParameter("key");
        String data = httpServletRequest.getParameter("data");

        if(filterOs.indexOf(headerInfo.getOs().toString()) > -1 || (headerInfo.getOs_num() != null && headerInfo.getOs_num().equals("7"))){
            String base64str = new String(Base64.decodeBase64(data.getBytes()), "UTF-8");
            httpServletRequest.setAttribute("data", base64str);
            httpServletRequest.setAttribute("encript", false);
        }else{
            String originalKey = getOriginalKey(httpServletRequest.getSession(), key, headerInfo);
            httpServletRequest.setAttribute("encript", true);
            if(key == null || originalKey == null){
                responseVO.code = Constant.UNKNOW_ERROR;
                responseVO.message = "密钥为空！";
                responseVO.data = "";
                String result = GsonUtils.toJson(responseVO);
                writeJson(httpServletResponse, result);
                return false;
            }
            //      stack.setValue("originalKey",originalKey);
            httpServletRequest.setAttribute("data", threeDESService.decryptMode(originalKey, data));
            httpServletRequest.setAttribute("originalKey", originalKey);
        }
        return true;
    }

    protected void handleValidationInfo(HeaderInfo headerInfo, CommonData common, ValidationInfo validationInfo) {
        headerInfo.setMid(common.getMid());
        if (common.getCityCode().length() != 0) {
            headerInfo.setCityCode(common.getCityCode());
        }
        String unique = common.getUnique();
        if (unique != null && !unique.equals("")) {
            headerInfo.setUnique(unique);
        }
        String userId = common.getUserId();
        if (userId != null && !userId.equals("")) {
            headerInfo.setUserId(common.getUserId());
        }
        validationInfo.setHeight(headerInfo.getHeight());
        validationInfo.setWidth(headerInfo.getWidth());
        validationInfo.setUserId(headerInfo.getUserId());
        validationInfo.setOs(headerInfo.getOs());
        validationInfo.setUnique(headerInfo.getUnique());
        validationInfo.setAppVersion(headerInfo.getAppVersion());
        validationInfo.setSourceId(headerInfo.getSourceId());
        validationInfo.setUserIp(headerInfo.getUserIp());
        validationInfo.setHeaderInfo(headerInfo.toString());
        validationInfo.setCityCode(headerInfo.getCityCode());
        validationInfo.setMid(headerInfo.getMid());
        validationInfo.setUdid(headerInfo.getUdid());
        validationInfo.setHeaderData(headerInfo.getData());
    }

    private String getOriginalKey(HttpSession session, String key, HeaderInfo headerInfo) throws Exception {

        String redisKey = headerInfo.getUnique();
        if(headerInfo.getOs().equals("html5") || (headerInfo.getOs_num() != null && headerInfo.getOs_num().equals("7"))){
            redisKey = session.getId();
        }
        String privateKeys  = rsaSessionCacheService.getRsaSessionKey(redisKey);

        if(privateKeys == null || privateKeys.equals("")){
            return null;
        }
        //解密对称密钥
        String originalKey = "";
        try{
            if (key != null && !key.equals("")) {
                originalKey = RSAUtil.decrypt(key, privateKeys);
            }
        }catch (Exception e){
            logger.error("ThreeDESInterceptor.getOriginalKey error,msg:" + e.getMessage(), e);
            logger.error("privateKeys-->"+privateKeys);
            logger.error("key-->"+key);
            return null;
        }
        return originalKey;
    }
}
