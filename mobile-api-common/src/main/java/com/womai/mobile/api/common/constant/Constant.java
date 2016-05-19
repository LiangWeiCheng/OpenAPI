package com.womai.mobile.api.common.constant;

/**
 * Created by Administrator on 2015/10/23.
 */
public class Constant {

    /**************             正确返回                ************/

    public static final String SUCCESS = "00";

    /**************             错误返回，code为正                ************/
    //服务异常
    public static final String SERVER_ERROR = "01";
    //科码返回错误
    public static final String KEMA_RETURN_ERROR = "02";
    //微信支付返回错误
    public static final String WXPAY_RETURN_ERROR = "03";
    //未知错误
    public static final String UNKNOW_ERROR = "99";

    public static final String FORNT_LINK_SERVER_ERROR = "11";

    /**************             错误返回，code为负                ************/

    public static final String SESSION_TIMEOUT = "-01";

    public static final String FORNT_ERROR = "-03";

    public static final String TEST_ERROR = "-06";

    public static final String CONSULT_FAIL = "-07";

    public static final String PATH_ISNULL = "-09";

    public static final String CMS_RETURNNULL = "-10";

    public static final String KEMA_RETURNNULL = "-11";

    public static final String WXPAY_RETURNNULL = "-12";

    public static final String CART_RETURNNULL = "-13";
}
