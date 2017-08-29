package com.gjyxfs.service.imp;

import com.gjyxfs.service.ShowService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/7/29.
 */
@Service
public class ShowServiceImpl implements ShowService{
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 模拟一个需要耗时2s的数据库查询任务
    private static Callable<String> callable = new Callable<String>() {
        public String call() throws Exception {
            Thread.currentThread().sleep(5000);
            return UUID.randomUUID().toString();
        }
    };

    // guava线程池,用来产生ListenableFuture
    private static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    private LoadingCache<String, String> cplsCache = CacheBuilder.newBuilder().
            refreshAfterWrite(10, TimeUnit.SECONDS).
            build(new CacheLoader<String, String>() {

                @Override
                public String load(String key) throws Exception {
                    key = "testCache";
                    return callable.call();
                }

                @Override
                public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                    return service.submit(callable);
                }
            });


    private int ss = 0;

    public void show() {
        showBefore();
//        showError();//异常测试
        showEnd();
    }
    public void showBefore(){
        System.out.println("showBefore============");
    }
    public void showError(){
        System.out.println("showError============");
        throw new RuntimeException();
    }
    public void showEnd(){
        System.out.println("showEnd===============");
    }


    public void addSs(int ss) throws InterruptedException {
        if(ss == 100){

        }
        this.ss = this.ss + ss;
    }

    public int getSs() {
        return this.ss;
    }

    public LoadingCache<String, String> getCachedData() {
        return cplsCache;
    }
}
