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
public class ResponseProp {

    private static Properties responseProp =  new  Properties();

    private ResponseProp(String filePath) throws IOException {
        InputStream in;
        ClassLoader cl = ResponseProp.class.getClassLoader();
        if  (cl !=  null ) {
            in = cl.getResourceAsStream(filePath);
        }  else {
            in = ClassLoader.getSystemResourceAsStream(filePath);
        }
        responseProp.load(in);
        in.close();
    }

    public static String getMapValue(String propKey, String filePath) throws IOException {
        if(responseProp.size() == 0) {
            synchronized (ResponseProp.class){
                if(responseProp.size() == 0) {
                    new ResponseProp(filePath);
                }
            }
        }
        String beanName = responseProp.getProperty(propKey);
        return beanName == null ? null : beanName.trim();
    }
}
