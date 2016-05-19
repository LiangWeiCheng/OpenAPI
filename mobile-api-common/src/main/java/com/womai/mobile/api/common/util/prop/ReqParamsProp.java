package com.womai.mobile.api.common.util.prop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.common.util.prop
 * 创建者: Chang Jinan
 * 日  期: 2016/5/12
 * 时  间: 18:33
 * 描  述：
 */
public class ReqParamsProp {

    private static Properties reqParamsProp =  new  Properties();

    private ReqParamsProp(String filePath) throws IOException {
        InputStream in;
        ClassLoader cl = ReqParamsProp.class.getClassLoader();
        if  (cl !=  null ) {
            in = cl.getResourceAsStream(filePath);
        }  else {
            in = ClassLoader.getSystemResourceAsStream(filePath);
        }
        reqParamsProp.load(in);
        in.close();
    }

    public static String getMapValue(String propKey, String filePath) throws IOException {
        if(reqParamsProp.size() == 0) {
            synchronized (ReqParamsProp.class){
                if(reqParamsProp.size() == 0) {
                    new ReqParamsProp(filePath);
                }
            }
        }
        String beanName = reqParamsProp.getProperty(propKey);
        return beanName == null ? null : beanName.trim();
    }
}
