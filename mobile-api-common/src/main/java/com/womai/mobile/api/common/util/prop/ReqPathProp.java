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
public class ReqPathProp {

    private static Properties reqPathProp =  new  Properties();

    private ReqPathProp(String filePath) throws IOException {
        InputStream in;
        ClassLoader cl = ReqPathProp.class.getClassLoader();
        if  (cl !=  null ) {
            in = cl.getResourceAsStream(filePath);
        }  else {
            in = ClassLoader.getSystemResourceAsStream(filePath);
        }
        reqPathProp.load(in);
        in.close();
    }

    public static String getMapValue(String propKey, String filePath) throws IOException {
        if(reqPathProp.size() == 0) {
            synchronized (ReqPathProp.class){
                if(reqPathProp.size() == 0) {
                    new ReqPathProp(filePath);
                }
            }
        }
        String beanName = reqPathProp.getProperty(propKey);
        return beanName == null ? null : beanName.trim();
    }
}
