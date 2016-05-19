package com.womai.mobile.api.common.util.prop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.common.util.prop
 * 创建者: Chang Jinan
 * 日  期: 2016/5/12
 * 时  间: 18:25
 * 描  述：
 */
public class ExceptionProp {

    private static Properties exceptionProp =  new  Properties();

    private ExceptionProp(String filePath) throws IOException {
        InputStream in;
        ClassLoader cl = ExceptionProp.class.getClassLoader();
        if  (cl !=  null ) {
            in = cl.getResourceAsStream(filePath);
        }  else {
            in = ClassLoader.getSystemResourceAsStream(filePath);
        }
        exceptionProp.load(in);
        in.close();
    }

    public static String getMapValue(String propKey, String filePath) throws IOException {
        if(exceptionProp.size() == 0) {
            synchronized (ExceptionProp.class){
                if(exceptionProp.size() == 0) {
                    new ExceptionProp(filePath);
                }
            }
        }
        String beanName = exceptionProp.getProperty(propKey);
        return beanName == null ? null : beanName.trim();
    }
}
