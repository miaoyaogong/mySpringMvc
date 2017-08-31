package com.gjyxfs.service.imp;

import com.gjyxfs.service.TestAopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by 1 on 2017/8/17.
 */
@Service
public class TestAopServiceImpl implements TestAopService {


    public void showBefore()  {
        System.out.println("演员们在化妆...");
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("表演开始...");
    }

    public void showIng() {
        System.out.println("胸碎大石...");
        try {
            Thread.currentThread().sleep(3000);
            System.out.println("金枪刺喉...");
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if((int)(Math.random()*10) > 5){
            System.out.println("啊，抱歉，刺喉失败...呜呜呜~~");
            throw new RuntimeException();
        }
    }

    public void showEnd() {
        System.out.println("表演圆满结束，鼓掌~~~");
    }

    public String show(){
        showBefore();
        showIng();//异常测试
        showEnd();

        return "finish";
    }

}
