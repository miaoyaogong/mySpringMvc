package com.gjyxfs.service;

/**
 * Created by Administrator on 2017/7/29.
 */
public class AudienceAspect {
    public void taskSeats(){
        System.out.println("等候节目开始===");
    }
    public void applaud(){
        System.out.println("鼓掌=========");
    }
    public void demandRefund(){
        System.out.println("退钱离场======");
    }
}
