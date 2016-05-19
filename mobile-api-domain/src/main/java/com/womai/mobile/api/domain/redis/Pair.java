package com.womai.mobile.api.domain.redis;

/**
 * Created with IntelliJ IDEA.
 * User: jijun.yang
 * Date: 13-10-25
 * Time: 下午7:23
 * To change this template use File | Settings | File Templates.
 */
/**
 * 键值对
 * @version V1.0
 * @author fengjc
 * @param <K> key
 * @param <Object> value
 */
public class Pair<K, Object> {

    private K key;
    private Object value;

    public Pair(K key, Object value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}