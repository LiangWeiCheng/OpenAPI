package com.womai.mobile.api.service.common.http.impl;

import com.womai.common.utils.HttpUtils4;
import com.womai.common.utils.MultiThreadHttpClient4Manager;
import com.womai.mobile.api.service.BaseService;
import com.womai.mobile.api.service.common.http.HttpService;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/11/11.
 */
@Service("httpService")
public class HttpServiceImpl extends BaseService implements HttpService {

    @Autowired
    private MultiThreadHttpClient4Manager multiThreadHttpClient4Manager;

    /**
     * http POST
     * @param url: url
     * @param header: header
     * @param params: entity params
     * @return
     * @throws Exception
     */
    public String doPOST(String url, Map<String, String> header, Map<String, String> params) throws Exception {
        CloseableHttpClient httpClient = multiThreadHttpClient4Manager.getHttpClient();
        StringBuilder stb = new StringBuilder();
        stb.append("POST url:" + url);
        stb.append(";header:" + header.toString());
        stb.append(";params:" + params.toString());
        long start = System.currentTimeMillis();
        String response = HttpUtils4.doPost(url, params, httpClient, "UTF-8", header);
        long end = System.currentTimeMillis();
        stb.append(";execute-time:" + (end - start));
        stb.append(";response:" + new String(Base64.encodeBase64(response.getBytes()), "UTF-8"));
        logger.info(stb.toString());
        return response;
    }

    /**
     * http GET
     * @param url: url
     * @param header: header
     * @param params: entity params
     * @return
     * @throws Exception
     */
    public String doGET(String url, Map<String, String> header, Map<String, String> params) throws Exception{
        CloseableHttpClient httpClient = multiThreadHttpClient4Manager.getHttpClient();
        StringBuilder stb = new StringBuilder();
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(url);
        url = m.replaceAll("");
        stb.append("GET url:" + url);
        stb.append(";header:" + header.toString());
        if(params != null) {
            stb.append(";params:" + params.toString());
        }
        long start = System.currentTimeMillis();
        String response = HttpUtils4.doGet(url, params, httpClient, "UTF-8", header);
        long end = System.currentTimeMillis();
        stb.append(";execute-time:" + (end - start));
        stb.append(";response:" + new String(Base64.encodeBase64(response.getBytes()), "UTF-8"));
        logger.info(stb.toString());
        return response;
    }
}