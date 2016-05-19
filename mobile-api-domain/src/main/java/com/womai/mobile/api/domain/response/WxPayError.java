package com.womai.mobile.api.domain.response;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.domain.response
 * 创建者: Chang Jinan
 * 日  期: 2016/5/18
 * 时  间: 13:54
 * 描  述：
 */
public class WxPayError {

    /**
     * 错误成功标识
     */
    private String errcode = "" ;

    /**
     * 错误提示信息
     */
    private String errmsg = "" ;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
