package com.womai.mobile.api.web.controller.function;

import com.fasterxml.jackson.databind.JsonNode;
import com.womai.mobile.api.common.constant.Constant;
import com.womai.mobile.api.common.util.GsonUtilsExt;
import com.womai.mobile.api.common.util.RSAUtil;
import com.womai.mobile.api.common.util.ResponseUtil;
import com.womai.mobile.api.common.util.ThreeDES;
import com.womai.mobile.api.domain.Consult;
import com.womai.mobile.api.domain.ConsultContext;
import com.womai.mobile.api.domain.HeaderInfo;
import com.womai.mobile.api.domain.response.ResponseVO;
import com.womai.mobile.api.service.common.rsaSession.RsaSessionCacheService;
import com.womai.mobile.api.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.service.common
 * 创建者: Chang Jinan
 * 日  期: 2016/5/11
 * 时  间: 11:42
 * 描  述：
 */
@Component("rsaController")
public class RsaController extends BaseController {

    @Autowired
    private RsaSessionCacheService rsaSessionCacheService;

    //固定密钥
    @Value("${3DES.key}")
    private String originalKey;
    //固定字符串
    @Value("${3DES.string}")
    private String str;
    @Value("${KEY.Test}")
    private String womaikey;

    private ResponseVO responseVO;

    @Override
    public ResponseVO execute(HttpServletRequest request) throws Exception {
        HeaderInfo headerInfo = (HeaderInfo) getHeaderInfo(request);

        if (!womaikey.equals(headerInfo.getTest())) {
            String msg = ResponseUtil.getMessage(Constant.TEST_ERROR);
            return responseVO = ResponseUtil.getResponseVO(Constant.TEST_ERROR, msg);
        }

        JsonNode jsonData = getJsonData(request);
        String consult = jsonData.findValue("consult").asText();
        if (!str.equals(consult)) {
            String msg = ResponseUtil.getMessage(Constant.CONSULT_FAIL);
            return responseVO = ResponseUtil.getResponseVO(Constant.CONSULT_FAIL, msg);
        }

        //生成非对称密钥
        //获得 公钥 私钥
        Map<String, String> keyMap = RSAUtil.generateKeyPair();
        //得到公钥
        String publickey = keyMap.get(RSAUtil.PUBLIC_KEY);
        //得到魔术值
        String modulus = keyMap.get(RSAUtil.MODULUS);
        //得到私钥
        String privateKeys = keyMap.get(RSAUtil.PRIVATE_KEY);

        //将私钥放入session中
        //this.getSession().setAttribute(RSAUtil.PRIVATE_KEY, privateKeys);
        String unique = headerInfo.getUnique();
        if(unique == null){
            unique = "1";
        }
        //修改 除了html5 外 不需要 sessionId
        String redisKey =  unique;

        //这里的 html5 特殊对待 不拼接  unique
        String os = headerInfo.getOs();
        String os_num = headerInfo.getOs_num();
        if (os.equals("html5") || (os_num != null && os_num.equals("7"))){
            redisKey = request.getRequestedSessionId();
        }

        rsaSessionCacheService.setRsaSessionKey(redisKey, privateKeys);

        //封装json
        String response = consultToJson(publickey, modulus);

        //封装返回报文
        String msg = ResponseUtil.getMessage(Constant.SUCCESS);
        responseVO = ResponseUtil.getResponseVO(Constant.SUCCESS, msg);
        //对称密钥加密公钥
        responseVO.data = ThreeDES.orginalEncoded(originalKey, response);
        return responseVO;
    }

    /**
     *协商返回信息
     * @param public_key
     * @param modulus_key
     * @return
     */
    private String consultToJson(String public_key, String modulus_key){
        Consult consult = new Consult();
        consult.setResponse("consult");
        ConsultContext context = new ConsultContext();
        context.setPublic_key(public_key);
        context.setModulus_key(modulus_key);
        consult.setConsult(context);
        return GsonUtilsExt.toJson(consult);
    }
}
