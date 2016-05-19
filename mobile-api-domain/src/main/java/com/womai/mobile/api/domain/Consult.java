package com.womai.mobile.api.domain;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.womai.mobile.api.domain.response.ResError;

/**
 * 错误信息
 */
public class Consult {
    /**
     * 响应信息
     */
    private String response;

    /**
     * 错误信息
     * 不拼接null注解
     */
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private ResError error;

    /**
     * 协商内容
     */
    private ConsultContext consult;

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

    public ConsultContext getConsult() {
        return consult;
    }

    public void setConsult(ConsultContext consult) {
        this.consult = consult;
    }
}
