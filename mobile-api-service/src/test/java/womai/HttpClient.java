package womai;

import com.womai.common.utils.GsonUtils;
import com.womai.mobile.api.common.util.GsonUtilsExt;
import com.womai.mobile.api.common.util.RSAUtil;
import com.womai.mobile.api.common.util.ThreeDES;
import com.womai.mobile.api.domain.CommonData;
import com.womai.mobile.api.domain.response.ResponseVO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * http 测试
 * Created with IntelliJ IDEA.
 * User: jijun.yang
 * Date: 13-7-5
 * Time: 上午11:53
 * To change this template use File | Settings | File Templates.
 */
public class HttpClient {
    private static Log log = LogFactory.getLog(HttpClient.class);
    private HttpPost mHttpPost = null;

    private DefaultHttpClient mHttpClient = null;

    public static String JSESSIONID = null;

    private static String originalKeyString = "qwertyuiopasdfghjkl;'zxc";
    private static String src = "abc";

    private static boolean keyflag = false;


    public static String post(String url, Map<String, String> headerMap, Map<String, Object> params)
            throws Exception {
        Jedis redis = new Jedis ("10.0.27.104",6379);
        log.info("加密后的数据 url=" + url + ",\n headerMap=" + headerMap + ",\n params=" + params);
        System.out.println("====url=" + url);

        HttpPost mHttpPost = null;
        DefaultHttpClient mHttpClient = null;

        mHttpPost = new HttpPost(url);

        // 第一次一般是还未被赋值，若有值则将SessionId发给服务器
        JSESSIONID = redis.get("JSESSIONID");
        if (!"".equals( JSESSIONID)&& JSESSIONID!=null) {
            mHttpPost.setHeader("Cookie", "JSESSIONID=" + JSESSIONID);
        }

        setHttpHeaders(mHttpPost, headerMap);
        if (params != null) {
            ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
            Iterator<String> iterator = params.keySet().iterator();
            String key;
            String buffer = "?";
            while (iterator.hasNext()) {
                key = iterator.next();
                pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
                buffer = buffer + key + "=" + params.get(key) + "&";
            }

            UrlEncodedFormEntity uefe = new UrlEncodedFormEntity(pairs, "GBK");
            // String json = readTextFile(uefe.getContent());
            mHttpPost.setEntity(uefe);
        }
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 300000);
        HttpConnectionParams.setSoTimeout(httpParams, 300000);
        mHttpClient = new DefaultHttpClient(httpParams);


        HttpResponse httpResponse = mHttpClient.execute(mHttpPost);

        CookieStore mCookieStore = mHttpClient.getCookieStore();
        List<Cookie> cookies = mCookieStore.getCookies();
        for (int i = 0; i < cookies.size(); i++) {
            // 这里是读取Cookie['JSESSIONID']的值存在静态变量中，保证每次都是同一个值
            if("".equals(JSESSIONID ) ||JSESSIONID == null){
                if ("JSESSIONID".equals(cookies.get(i).getName())) {
                    JSESSIONID = cookies.get(i).getValue();
                    redis.set("JSESSIONID",JSESSIONID);
                    break;
                }
            }
        }

        if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new IOException();
        }
        HttpEntity httpEntity = httpResponse.getEntity();

        String data = EntityUtils.toString(httpEntity, "UTF-8");
        // System.out.println("===========data2=" + data);
        if(data.indexOf("-01")!=-1){
            redis.set("JSESSIONID","");

        }
        return data;
    }


    private static void setHttpHeaders(AbstractHttpMessage httpMessage,
                                       Map<String, String> headers) {
        Iterator<String> iterator = headers.keySet().iterator();
        String headerName = "";
        String headerValue = "";
        while (iterator.hasNext()) {
            headerName = iterator.next();
            headerName = headerName.trim();
            headerValue = headers.get(headerName);
            httpMessage.setHeader(headerName, headerValue);
            System.out.println("name="+headerName+"--"+headerValue);
        }

    }

    public static String getCurrentDateTime() {
        String str = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        str = formatter.format(new Date());
        return str;
    }

    public static Map<String, String> hearderMap() {
        Jedis redis = new Jedis ("10.0.27.104",6379);
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("udid", "02:00:00:00:00:00");
        headerMap.put("os", "1");
        headerMap.put("osVersion", "2.9.0");
        headerMap.put("appVersion", "2.9.0");
        headerMap.put("sourceId", "1024");
        headerMap.put("ver", "1.0");
        headerMap.put("test", "mwomai01");
        headerMap.put("idfa","ADA93097-DA03-4595-B264-153CE7E2A6AD");
        //分辨率要变化      1920x1080
        headerMap.put("height", "1920");
        headerMap.put("width", "1080");
        headerMap.put("userId","54215632");
        headerMap.put("time", getCurrentDateTime());
        headerMap.put("unique","b0022eb48b975ba0bf7d8969e0af86a9");

        headerMap.put("level",redis.get("level"));
        System.out.println("redis.get(\"level\")"+redis.get("level"));
        headerMap.put("Accept-Encoding", "gzip, deflate");
        headerMap.put("Connection", "Keep-Alive");
        // httpPost.addHeader("Accept-Encoding", "gzip");
        //"Connection", "Keep-Alive"
        return headerMap;
    }



    /**
     * generate a new password random
     *
     * @param length
     * @return
     */
    public static String genrateRandomPassword(int length) {
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z'};

        int i;
        int pwd_len = length;
        int count = 0;
        int maxNum = str.length;
        StringBuffer pwd = new StringBuffer("");
        SecureRandom r = new SecureRandom();
        while (count < pwd_len) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
    public static byte[] orginalDecryptMode(String duichenKey, String datas) {
        //解密
        byte[] Miwen = Base64.decodeBase64(datas.getBytes());

        byte[] srcBytes = ThreeDES.decryptMode(duichenKey.getBytes(), Miwen);
        return srcBytes;
    }

    class Cosult {
        public String consult;
    }

    public static String consult(String url) throws Exception {
        /**
         *  生成对称密钥
         原始密钥 长度必须为24字符
         固定的对称密钥
         */
        String urlString = url + "consult";
        String hearder = "";


        Map<String, String> headerMap = new HashMap<String, String>();
        hearder = GsonUtilsExt.toJson(hearderMap());
        hearder = ThreeDES.orginalEncoded(originalKeyString, hearder);
        headerMap.put("headerData", hearder);


        Map<String, Object> paramMap = new HashMap<String, Object>();
        Cosult cosult = new HttpClient().new Cosult();
        cosult.consult = src;
        paramMap.put("data", cosult);


        CommonData c = new CommonData();
        c.setMid("100");
        paramMap.put("common", c);
        String paramJson = GsonUtilsExt.toJson(paramMap);
        String base64str = ThreeDES.orginalEncoded(originalKeyString, paramJson);
        System.out.println("paramJson=" + paramJson);
        paramMap = new HashMap<String, Object>();
        paramMap.put("data", base64str);
        /**
         * 协商
         */
        String rsaPublicKey = post(urlString, headerMap, paramMap);
        //解密

        Map<String, String> resMap = GsonUtilsExt.toMap(rsaPublicKey);

        byte[] srcBytes = orginalDecryptMode(originalKeyString, resMap.get("data"));
        Map<String, Map<String, String>> consult = GsonUtilsExt.toObject(new String(srcBytes), new HashMap<String, Map<String, String>>().getClass());

        Map<String, String> cc = (Map<String, String>) consult.get("consult");

        rsaPublicKey = cc.get("public_key");
        return rsaPublicKey;
    }

    public static String postEncrypt(String url, String method) throws Exception {
        Jedis redis = new Jedis ("10.0.27.104",6379);
        String rsaPublicKey = "";
        String keyflag = redis.get("keyflag");

        if(keyflag != null || keyflag.equals("")){
            String originalKeyString = "qwertyuiopasdfghjkl;'zxc";
            //协商
            rsaPublicKey = consult(url);
            redis.set("rsaPublicKey",rsaPublicKey);
            redis.set("keyflag","keyflag");
        }
        rsaPublicKey = redis.get("rsaPublicKey");
        //生成3des随机密钥
        String duichenKey = genrateRandomPassword(24);
        //通用rsa公钥加密对称密钥
        String jiamiKEy = RSAUtil.encrypt(duichenKey, rsaPublicKey);
        /**
         * 将加密后的数据和加密后的对称密钥传输给服务
         */


        String urlString = url + method;

        Map<String, String> headerMap = new HashMap<String, String>();
        String hearder = GsonUtils.toJson(hearderMap());
        hearder = ThreeDES.orginalEncoded(originalKeyString, hearder);
        headerMap.put("headerData", hearder);

        String data = "";
        Map paramMap = new HashMap();
        CommonData c = new CommonData();

        if(method.equals("userinfo")){
            Map map = new HashMap() ;
            map.put("userId","54215965");
            map.put("userSession","83d751d15a7f618def125b6fdd934e7a");
            paramMap.put("data",map);
        }

        c.setMid("100");
        c.setCityCode("31000");
        c.setUserId("57600194");
        c.setUserSession("4c5eabac56df17cd1057d9e2be091a7f");
        c.setTest1("a4532c14d3150c6562b1bcc20e55d632");
        paramMap.put("common", c);
        data = GsonUtils.toJson(paramMap);
        System.out.print("_______________data=" + data);
        String reqbase64param = "";
        if("5".equals(hearderMap().get("os"))) {
            reqbase64param = new String(Base64.encodeBase64(data.getBytes()), "UTF-8");
        }
        else {
            reqbase64param = ThreeDES.orginalEncoded(duichenKey, data);
        }
        paramMap = new HashMap<String, Object>();
        //rsa 加密的对称密钥
        paramMap.put("key", jiamiKEy);
        //组装最终data
        paramMap.put("data",reqbase64param);

        //与前置机交互
        String datas = post(urlString, headerMap, paramMap);
        System.out.println("-->原始="+datas);
        Map<String, String> resMap = GsonUtilsExt.toMap(datas);
        String code = resMap.get("code");
        String returnStr = "";
        if(code.equals("-01")){
            System.out.println("--》"+resMap.get("message"));
            redis.set("keyflag","");
            postEncrypt(url,method);

        }else{
            if("5".equals(hearderMap().get("os"))) {
                returnStr = new String(Base64.decodeBase64(resMap.get("data").getBytes()), "UTF-8");
                System.out.println("解密后的字符串:" + returnStr);
            }
            else {
                byte[] srcBytes = orginalDecryptMode(duichenKey, resMap.get("data"));
                returnStr = new String(srcBytes);
                System.out.println("解密后的字符串:" + (new String(srcBytes)));
            }
//            if(method.equals("login")){
//                ROLoginUser roLoginUser = JacksonUtil.toObject(returnStr, ROLoginUser.class);
//                redis.set("test1",roLoginUser.test1);
//                redis.set("usersession",roLoginUser.usersession);
//                redis.set("userid",roLoginUser.userid);
//
//            }
//            if(method.equals("userinfo")){
//                ROUserInfo roLoginUser = JacksonUtil.toObject(returnStr, ROUserInfo.class);
//                redis.set("level",roLoginUser.userinfo.level);
//
//            }
        }
        return returnStr;
    }

    public static String postEncryptwg(String url, String method) throws Exception {
        //客户端对称密钥
        String originalKeyString = "qwertyuiopasdfghjkl;'zxc";
        //url
        String urlString = url + "home";

        urlString = url + method;
        //公共头key
        String hearder = "";
        String data = "";
        //des加密
        Map<String, String> headerMap = new HashMap<String, String>();
        hearder = GsonUtils.toJson(hearderMap());
        hearder = ThreeDES.orginalEncoded(originalKeyString, hearder);
        // hearder = ThreeDES.orginalEncoded(originalKeyString, "");
        headerMap.put("headerData", hearder);

        Object paramData = null;

        if (method.equals("activity/cityActivity")) {
            Map<String, Object> carMap = new HashMap<String, Object>();
            carMap.put("sid", "162");
            carMap.put("mid", "0");
            paramData = carMap;
        }

        //商品详情
        if (method.equals("product")) {
            Map<String, Object> carMap = new HashMap<String, Object>();
            carMap.put("productid", "571019");
            paramData = carMap;
        }

        Map paramMap = new HashMap();
        CommonData c = new CommonData();
        c.setMid("0");
        c.setCityCode("31000");

        paramMap.put("common", c);
        paramMap.put("data", paramData);
        data = GsonUtils.toJson(paramMap);

        paramMap.put("data", new String(Base64.encodeBase64(data.getBytes()), "UTF-8"));

        log.info("加密前的数据 url="+urlString+"\n data = "+ new String(Base64.encodeBase64(data.getBytes()), "UTF-8")+",\n headerMap="+c+",\n paramMap="+data);
        //返回明文数据
        String datas = post(urlString, headerMap, paramMap);
        log.info("访问以后得到的返回值 datas="+datas);

        System.out.println("访问以后得到的返回值 datas="+datas);
        ResponseVO responseVO = GsonUtils.fromJson(datas, ResponseVO.class);
        String responseBase64 = new String(Base64.decodeBase64(responseVO.data.toString().getBytes()), "UTF-8");

        System.out.println("解密前的字符串:" + responseBase64);
        //ROCar roCar = JacksonUtil.toObject(responseBase64, ROCar.class);
        return    responseBase64;
    }


    public static void main(String[] args) throws Exception {

        int pre = (int) System.currentTimeMillis();
        String url = "http://10.0.0.157:8011/wapi/";

        url = "http://127.0.0.1:8088/";
//        url = "http://127.0.0.1:8080/mcarts/";
//        url = "http://10.6.26.72:8011/";
//        url = "http://cart.m.womai.test/";

        //活动
//        postEncryptwg(url, "activity/cityActivity");
//        postEncryptwg(url, "product");
//        postEncrypt(url, "activity/postCityActivity");
        postEncrypt(url, "userinfo");

        int post = (int) System.currentTimeMillis();

        System.out.println((post - pre) * 1.0 / 1000);
        System.out.println(System.currentTimeMillis());
    }


}
