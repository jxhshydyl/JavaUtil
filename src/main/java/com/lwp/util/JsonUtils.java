package com.lwp.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Json工具类
 */
public class JsonUtils {

    /**
     * java对象转成json字符串
     * @param obj 源对象
     * @return JSON字符串
     */
    public static String objToJson(Object obj) {
        if (obj != null) {
            return JSONObject.toJSONString(obj);
        }
        return null;
    }

    /**
     * json字符串转成java对象
     * @param json JSON字符串
     * @param clazz 转成的目标对象类型
     * @param <T> 目标对象
     * @return
     */
    public static <T> T jsonToObj(String json, Class<T> clazz) {
        return JSONObject.parseObject(json, clazz);
    }
}
