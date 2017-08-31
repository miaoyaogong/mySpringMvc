package com.gjyxfs.controller;

import com.gjyxfs.model.User;
import com.gjyxfs.service.IUserService;
import com.gjyxfs.service.CacheService;
import com.gjyxfs.service.TestAopService;
import com.gjyxfs.util.JsonMapper;
import com.google.common.base.Stopwatch;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2017/7/29.
 */
@Controller
public class HomeController {
    //添加一个日志器
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private CacheService cacheService;

    @Autowired
    private IUserService userService;

    @RequestMapping("/")
    @ResponseBody
    public String index(HttpServletRequest request){
        return "HelloWorld";
    }

    @RequestMapping("/cache")
    @ResponseBody
    public void cache(HttpServletRequest request) throws ExecutionException, InterruptedException {
        while(true){
            Stopwatch timer = Stopwatch.createStarted();
            LoadingCache<String, String> ss = cacheService.getCachedData();
            logger.info("返回结果;{} 耗时：{}", ss.get("testCache"), timer.stop());
            Thread.currentThread().sleep(1000);
        }

    }

    @RequestMapping("/getAllUsers")
    @ResponseBody
    public String getAllUsers(HttpServletRequest request) throws ExecutionException {
        List<User> users =  userService.getAllUsers();
        return JsonMapper.getInstance().toJson(users);
    }
}
