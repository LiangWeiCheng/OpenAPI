package com.womai.mobile.api.domain.response;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.domain.response
 * 创建者: Chang Jinan
 * 日  期: 2016/5/17
 * 时  间: 16:52
 * 描  述：
 */
public class KemaError {

    /**
     * 响应信息
     */
    private String response;

    /**
     * 错误信息
     */
    private ResError error;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ResError getError() {
        return error;
    }

    public void setError(ResError error) {
        this.error = error;
    }
}
