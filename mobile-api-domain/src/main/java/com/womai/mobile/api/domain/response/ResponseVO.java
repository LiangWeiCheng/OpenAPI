package com.womai.mobile.api.domain.response;

/**
 * Created by Administrator on 2015/10/23.
 */
public class ResponseVO {

    public String code;

    public String message;

    public String data;

    public ResponseVO() {
    }

    public ResponseVO(String code, String message) {
        this.message = message;
        this.code = code;
    }
}
