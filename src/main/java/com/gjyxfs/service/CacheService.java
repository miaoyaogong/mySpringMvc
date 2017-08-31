package com.gjyxfs.service;

import com.google.common.cache.LoadingCache;

import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */
public interface CacheService {

    LoadingCache<String, String> getCachedData();
}
