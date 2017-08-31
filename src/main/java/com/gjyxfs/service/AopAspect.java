package com.gjyxfs.service;

/**
 * Created by Administrator on 2017/7/29.
 */
public class AopAspect {
    public void before(){
        System.out.println("--------等候开始--------");
    }
    public void after(){
        System.out.println("--------鼓掌--------");
    }
    public void exeException(){
        System.out.println("--------退钱离场--------");
    }
}
