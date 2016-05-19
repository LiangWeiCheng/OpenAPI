package com.womai.mobile.api.common.util.prop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.common.util.prop
 * 创建者: Chang Jinan
 * 日  期: 2016/5/12
 * 时  间: 18:34
 * 描  述：
 */
public class ControllerProp {

    private static Properties controllerProp =  new  Properties();

    private ControllerProp(String filePath) throws IOException {
        InputStream in;
        ClassLoader cl = ControllerProp.class.getClassLoader();
        if  (cl !=  null ) {
            in = cl.getResourceAsStream(filePath);
        }  else {
            in = ClassLoader.getSystemResourceAsStream(filePath);
        }
        controllerProp.load(in);
        in.close();
    }

    public static String getMapValue(String propKey, String filePath) throws IOException {
        if(controllerProp.size() == 0) {
            synchronized (ControllerProp.class){
                if(controllerProp.size() == 0) {
                    new ControllerProp(filePath);
                }
            }
        }
        String beanName = controllerProp.getProperty(propKey);
        return beanName == null ? null : beanName.trim();
    }
}
