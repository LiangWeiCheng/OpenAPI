package com.womai.mobile.api.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: jijun.yang
 * Date: 13-6-19
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
public abstract class Util {

    public static String configToMap(String configstr, String os) {
        Map<String, String> configMap = new HashMap<String, String>();
        String[] oslist = configstr.split(",");
        for (String l : oslist) {
            String[] o = l.split(":");
            //序号， os
            configMap.put(o[0], o[1]);
        }
        return configMap.get(os);
    }

    /**
     * 对数据进行HMAC-MD5签名
     *
     * @param source - 待签名数据
     * @param key    - 密钥
     * @return - 数据签名结果
     */
    public static String cryptHmacMd5(String source, String key) {
        byte k_ipad[] = new byte[64];
        byte k_opad[] = new byte[64];
        byte keyb[];
        byte value[];
        try {
            keyb = key.getBytes("UTF-8");
            value = source.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            keyb = key.getBytes();
            value = source.getBytes();
        }

        Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
        Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
        for (int i = 0; i < keyb.length; i++) {
            k_ipad[i] = (byte) (keyb[i] ^ 0x36);
            k_opad[i] = (byte) (keyb[i] ^ 0x5c);
        }

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // Log.alert(com.mbi.security.Digest.class, (byte)1, "Cannot get
            // algorithm", e);
            return null;
        }
        md.update(k_ipad);
        md.update(value);
        byte dg[] = md.digest();
        md.reset();
        md.update(k_opad);
        md.update(dg, 0, 16);
        dg = md.digest();
        return toHex(dg);
    }

    public static String toHex(byte input[]) {
        if (input == null)
            return null;
        StringBuffer output = new StringBuffer(input.length * 2);
        for (int i = 0; i < input.length; i++) {
            int current = input[i] & 0xff;
            if (current < 16)
                output.append("0");
            output.append(Integer.toString(current, 16));
        }

        return output.toString();
    }

    public static String replaceNull(JsonNode jsonNode) {
        String context = "";
        if (jsonNode != null) {
            context = jsonNode.asText();
        }
        return context;
    }
}
