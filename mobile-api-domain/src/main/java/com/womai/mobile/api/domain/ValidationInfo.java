package com.womai.mobile.api.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jijun.yang
 * Date: 13-7-10
 * Time: 上午9:31
 * To change this template use File | Settings | File Templates.
 */
public class ValidationInfo {

    protected final String appkey="9980998";

    private String Mid = "";

    private String width = "";

    private String height = "";

    private String os = "";

    private String unique = "";

    private String appVersion = "";

    private String sourceId = "";

    private String userId = "";

    private String userIp = "";

    private String test;

    /**
     * 前置机请求服务器用
     */
    private String headerInfo;

    private String headerData="" ;

    public String activityId = "";

    private String promotionId = "";

    /***********安全数据需求加入经纬度数据**********/
    private String longitude = "";

    private String latitude = "";

    /*********手机标识传，IOS：idfa,Android：imei*********/
    private String imei = "";

    private String idfa = "";

    private String cpsKey = "";

    private String Udid = "";

    /**
     * 城市Code
     */
    private String cityCode = "";

    public String getAppkey() {
        return appkey;
    }

    public String getMid() {
        return Mid;
    }

    public void setMid(String mid) {
        Mid = mid;
    }

    public Map<String,String> toMap(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("Appkey",this.appkey);
        //对mid 以及分辨率 做兼容处理
        if(this.Mid == null || this.Mid.equals("")){
            this.Mid = "0";
        }
        map.put("Mid",this.Mid);
        if(this.width == null || this.width.equals("")){
            this.width = "480";
        }
        if(this.height == null || this.height.equals("")){
            this.height = "800";
        }
        map.put("Width",this.width);
        map.put("Height",this.height);
        map.put("Unique",this.unique);
        map.put("Appversion",this.appVersion);
        map.put("Sourceid",this.sourceId);
        map.put("Os",os);
        map.put("Userip",userIp);
        map.put("Userid",userId);
        map.put("headerInfo",headerInfo);
        map.put("headerData",headerData);
        map.put("ActivityId",activityId);
        map.put("Promotionid",promotionId);
        map.put("Longitude",longitude);
        map.put("Latitude",latitude);
        map.put("Imei",imei);
        map.put("Idfa",idfa);
        map.put("Baiducps",cpsKey);
        map.put("Cityid", cityCode);
        map.put("Udid", Udid);
        return map;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(String headerInfo) {
        this.headerInfo = headerInfo;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeaderData() {
        return headerData;
    }

    public void setHeaderData(String headerData) {
        this.headerData = headerData;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getCpsKey() {
        return cpsKey;
    }

    public void setCpsKey(String cpsKey) {
        this.cpsKey = cpsKey;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getUdid() {
        return Udid;
    }

    public void setUdid(String udid) {
        Udid = udid;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}

