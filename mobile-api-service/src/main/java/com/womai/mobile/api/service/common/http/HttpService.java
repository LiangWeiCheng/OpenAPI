package com.womai.mobile.api.service.common.http;

import java.util.Map;

/**
 * Created by Administrator on 2015/11/11.
 */
public interface HttpService {
    /**
     * http POST
     *
     * @param url:    url
     * @param header: header
     * @param params: entity params
     * @return
     * @throws Exception
     */
    public String doPOST(String url, Map<String, String> header, Map<String, String> params) throws Exception;

    /**
     * http GET
     *
     * @param url:    url
     * @param header: header
     * @param params: entity params
     * @return
     * @throws Exception
     */
    public String doGET(String url, Map<String, String> header, Map<String, String> params) throws Exception;
}
