package com.womai.mobile.api.service.common.threeDES.impl;

import com.womai.common.utils.GsonUtils;
import com.womai.mobile.api.common.util.ThreeDES;
import com.womai.mobile.api.service.BaseService;
import com.womai.mobile.api.service.common.threeDES.ThreeDESService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2015/10/23.
 */
@Service("threeDESService")
public class ThreeDESServiceImpl extends BaseService implements ThreeDESService {
    @Override
    public String decryptMode(String key, String src) {
        //如果key不为空则进行解密处理 否则 原文返回
        if (key != null && !key.equals("")) {
            byte[] Miwen = Base64.decodeBase64(src.getBytes());
            byte[] srcBytes = ThreeDES.decryptMode(key.getBytes(), Miwen);
            src = new String(srcBytes);
        }
        return src;
    }

    @Override
    public String encryptMode(String key, Object src) throws UnsupportedEncodingException {
        String result = "";
        if (!(src instanceof String)) {
            result = GsonUtils.toJson(src);
        } else {
            result = (String) src;
        }
        //如果key不为空则进行加密处理 否则 原文返回
        if (key != null && !key.equals("")) {
            byte[] encoded = null;
            encoded = ThreeDES.encryptMode(key.getBytes(),
                    result.getBytes("UTF-8"));
            //base64 编码  发送
            result = new String(Base64.encodeBase64(encoded), "UTF-8");
        }
        return result;
    }
}
