package com.gjyxfs.service;

import com.google.common.cache.LoadingCache;

import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */
public interface ShowService {
    void show();

    void addSs(int ss) throws InterruptedException;

    int getSs();

    LoadingCache<String, String> getCachedData();
}
