package com.womai.mobile.api.common.exception;

/**
 * Created with IntelliJ IDEA.
 * User: jijun.yang
 * Date: 13-7-31
 * Time: 上午10:17
 * To change this template use File | Settings | File Templates.
 */
public class MobileApiException extends Exception {
    private String message;

    public MobileApiException(String message) {

        super(message);    //To change body of overridden methods use File | Settings | File Templates.
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
