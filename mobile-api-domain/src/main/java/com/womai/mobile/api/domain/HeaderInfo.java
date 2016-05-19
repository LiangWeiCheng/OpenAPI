package com.womai.mobile.api.domain;

/**
 * Created with IntelliJ IDEA.
 * User: chenweiwei
 * Date: 13-7-9
 * Time: 下午4:39
 * To change this template use File | Settings | File Templates.
 */
public class HeaderInfo {

    private String udid = "";

    private String os = "" ;

    private String os_num = "" ;

    private String osVersion = "";

    private String sourceId = "";

    private String ver = "";

    private String test = "";

    private String time = "";

    private String appVersion = "";

    private String width = "";

    private String height = "";

    private String data = "";

    private String mid = "";

    private String userId = "";

    private String unique = "";

    private String userIp = "";

    private String level = "";

    private String idfa = "";

    private String imei = "";

    private String activityId = "";

    private String promotionId = "";

    /***********安全数据需求加入经纬度数据**********/
    private String longitude = "";

    private String latitude = "";

    private String cpsKey = "";

    /**
     * 城市id
     */
    private String cityCode = "";

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("udid:").append(udid)
                .append(";os:").append(os)
                .append(";osVersion:").append(osVersion)
                .append(";sourceId:").append(sourceId)
                .append(";ver:").append(ver)
                .append(";appVersion:").append(appVersion)
                .append(";userId:").append(userId)
                .append(";unique:").append(unique)
                .append(";userIp:").append(userIp)
                .append(";activityId:").append(activityId)
                .append(";promotionId:").append(promotionId)
                .append(";longitude:").append(longitude)
                .append(";latitude:").append(latitude)
                .append(";idfa:").append(idfa)
                .append(";imei:").append(imei)
                .append(";cpsKey:").append(cpsKey)
                .append(";cityId:").append(cityCode);
        return sb.toString();
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs_num() {
        return os_num;
    }

    public void setOs_num(String os_num) {
        this.os_num = os_num;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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
}
