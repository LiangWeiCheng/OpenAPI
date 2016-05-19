package com.womai.mobile.api.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: jijun.yang
 * Date: 13-10-22
 * Time: 上午10:31
 * To change this template use File | Settings | File Templates.
 */
public class SerializeUtil {

    private static Log log = LogFactory.getLog(SerializeUtil.class);

    public static byte[] serialize(Object object) {

        ObjectOutputStream oos = null;

        ByteArrayOutputStream baos = null;

        try {

            baos = new ByteArrayOutputStream();

            oos = new ObjectOutputStream(baos);

            oos.writeObject(object);

            byte[] bytes = baos.toByteArray();

            return bytes;

        } catch (Exception e) {
            log.error("SerializeUtil.serialize error"+e.getMessage(),e);

        } finally {
            if (oos != null) {
                try {
                    oos.close();
                    oos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    baos.close();
                    baos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }


    public static Object unserialize(byte[] bytes) {

        ByteArrayInputStream bais = null;

        try {

//反序列化
            if(bytes == null){
                return null;
            }
            bais = new ByteArrayInputStream(bytes);
            //System.out.println("==="+bais);
            ObjectInputStream ois = new ObjectInputStream(bais);
            //System.out.println("==="+ois.readObject());
            return ois.readObject();

        } catch (Exception e) {

            // e.printStackTrace();
        }

        return null;

    }
}
