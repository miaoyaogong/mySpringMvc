package com.gjyxfs.service.imp;

import com.gjyxfs.service.ShowService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/7/29.
 */
@Service
public class ShowServiceImpl implements ShowService{

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
}
