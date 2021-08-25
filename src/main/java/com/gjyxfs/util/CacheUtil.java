package com.gjyxfs.util;

import java.util.Date;

/**
 * 操作缓存的工具类
 *
 * @author wangbo
 * @date 2018-03-07 13:01:54
 */
public class CacheUtil {

    /**
     * 添加缓存
     *
     * @param key
     * @param value
     */
    public static boolean addCache(Object key, Object value) {
        CacheManage cm = CacheManage.getInstance();
        CacheConfModel cModel = new CacheConfModel();
        cModel.setForever(true);
        return cm.addCache(key, value, cModel);
    }

    /**
     * 添加临时缓存
     *
     * @param key
     * @param value
     * @param durableTime
     */
    public static boolean addTempCache(Object key, Object value, int durableTime) {
        CacheManage cm = CacheManage.getInstance();
        CacheConfModel cModel = new CacheConfModel();
        cModel.setBeginTime(new Date().getTime());
        cModel.setDurableTime(durableTime);
        cModel.setForever(false);
        return cm.addCache(key, value, cModel);
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public static Object getValue(Object key) {
        CacheManage cm = CacheManage.getInstance();
        Object ob = cm.getValue(key);
        if (ob == null) {
            return null;
        }
        return ob;
    }

    /**
     * 获取字符串缓存
     *
     * @param key
     * @return
     */
    public static String getStringValue(Object key) {
        CacheManage cm = CacheManage.getInstance();
        Object ob = cm.getValue(key);
        if (ob == null) {
            return null;
        }
        return ob.toString();
    }

}

