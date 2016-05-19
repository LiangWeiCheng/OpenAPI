package com.womai.mobile.api.domain;

/**
 * Created with IntelliJ IDEA.
 * User: jijun.yang
 * Date: 13-7-22
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public class CommonData {

    private String userId = "";

    private String userSession = "";

    private String mid = "0";

    private String test1 = "";

    /**
     * 压力测试用
     */
    private String unique = "";
    /**
     * 城市id
     */
    private String cityCode = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserSession() {
        return userSession;
    }

    public void setUserSession(String userSession) {
        this.userSession = userSession;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTest1() {
        return test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    @Override
    public String toString() {
        return "CommonData{" +
                "userId='" + userId + '\'' +
                ", userSession='" + userSession + '\'' +
                ", mid='" + mid + '\'' +
                ", test1='" + test1 + '\'' +
                ", cityCode='" + cityCode + '\'' +
                '}';
    }
}
