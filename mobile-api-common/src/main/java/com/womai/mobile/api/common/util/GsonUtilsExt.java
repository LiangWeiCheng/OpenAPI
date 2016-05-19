package com.womai.mobile.api.common.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.womai.common.utils.GsonUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/30.
 */
public class GsonUtilsExt extends GsonUtils {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * json字符串转对象
     *
     * @param json
     * @param type
     * @return
     */
    public static <T> T toObject(String json, Class<T> type) throws IOException {
        if (json == null) {
            return null;
        }
        return mapper.readValue(json, type);

    }

    /**
     * json字符串转JSONNode
     *
     * @param json
     * @return
     */
    public static JsonNode toJsonNode(String json) {
        if (json == null) {
            return null;
        }
        try {
            return mapper.readTree(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> toMap(String json) throws IOException {
        Map<String, String> mapclass = new HashMap<String, String>();
        return toObject(json, mapclass.getClass());
    }

    /**
     * 对象转json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {

            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
