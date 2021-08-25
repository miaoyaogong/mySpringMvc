package com.gjyxfs.util;

/**
 * 缓存配置实体类
 *
 * @author wangbo
 * @date 2018-03-07 12:42:56
 */
public class CacheConfModel implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private long beginTime;// 缓存开始时间

    private int durableTime;// 持续时间，秒

    private boolean isForever = false;// 是否持久

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public boolean isForever() {
        return isForever;
    }

    public void setForever(boolean isForever) {
        this.isForever = isForever;
    }

    public int getDurableTime() {
        return durableTime;
    }

    public void setDurableTime(int durableTime) {
        this.durableTime = durableTime;
    }

}
