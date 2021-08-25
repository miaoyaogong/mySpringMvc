package com.gjyxfs.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 缓存管理类
 *
 * @author wangbo
 * @date 2018-03-07 12:43:41
 */
public class CacheManage {

    private static Map<Object, Object> cacheMap = new HashMap<>();

    private static Map<Object, CacheConfModel> cacheConfMap = new HashMap<>();

    private static CacheManage cm = null;

    // 构造方法私有化
    private CacheManage() {
    }

    // 获取实例
    public static CacheManage getInstance() {
        if (cm == null) {
            cm = new CacheManage();
            // 第一次获取实例的时候启动线程
            Thread t = new ClearCache();
            t.start();
        }
        return cm;
    }

    /**
     * 添加缓存实体
     *
     * @param key
     * @param value
     * @param ccm
     * @return
     */
    public boolean addCache(Object key, Object value, CacheConfModel ccm) {
        System.out.println("开始增加缓存");
        boolean flag = false;
        try {
            cacheMap.put(key, value);
            cacheConfMap.put(key, ccm);
            System.out.println("增加缓存结束");
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 获取缓存实体
     *
     * @param key
     * @return
     */
    public Object getValue(Object key) {
        Object object = cacheMap.get(key);
        if (object != null) {
            return object;
        } else {
            return null;
        }
    }

    /**
     * 获取缓存数据的数量
     *
     * @return
     */
    public int getSize() {
        return cacheMap.size();
    }

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    public boolean removeCache(Object key) {
        boolean flag = false;
        try {
            cacheMap.remove(key);
            cacheConfMap.remove(key);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 清除缓存的线程
     */
    private static class ClearCache extends Thread {
        public void run() {
            while (true) {
                // 记录要清除的key
                Set<Object> tempSet = new HashSet<>();
                Set<Object> set = cacheConfMap.keySet();
                Iterator<Object> it = set.iterator();
                while (it.hasNext()) {
                    Object key = it.next();
                    CacheConfModel ccm = (CacheConfModel) cacheConfMap.get(key);
                    // 比较是否需要清除
                    if (!ccm.isForever()) {
                        if ((new Date().getTime() - ccm.getBeginTime()) >= ccm.getDurableTime() * 1000L) {
                            // 可以清除，先记录下来
                            tempSet.add(key);
                        }
                    }
                }
                // 真正清除
                Iterator<Object> tempIt = tempSet.iterator();
                while (tempIt.hasNext()) {
                    Object key = tempIt.next();
                    cacheMap.remove(key);
                    cacheConfMap.remove(key);
                }

                LocalDateTime localDateTime = LocalDateTime.now();
                System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "，缓存大小==>" + cacheMap.size());
                // 线程休息
                try {
                    Thread.sleep(60 * 10 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
