package com.womai.mobile.api.common.util.prop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.common.util.prop
 * 创建者: Chang Jinan
 * 日  期: 2016/5/12
 * 时  间: 18:35
 * 描  述：
 */
public class OptParamsProp {

    private static Properties optParamsProp =  new  Properties();

    private OptParamsProp(String filePath) throws IOException {
        InputStream in;
        ClassLoader cl = OptParamsProp.class.getClassLoader();
        if  (cl !=  null ) {
            in = cl.getResourceAsStream(filePath);
        }  else {
            in = ClassLoader.getSystemResourceAsStream(filePath);
        }
        optParamsProp.load(in);
        in.close();
    }

    public static String getMapValue(String propKey, String filePath) throws IOException {
        if(optParamsProp.size() == 0) {
            synchronized (OptParamsProp.class){
                if(optParamsProp.size() == 0) {
                    new OptParamsProp(filePath);
                }
            }
        }
        String beanName = optParamsProp.getProperty(propKey);
        return beanName == null ? null : beanName.trim();
    }
}
