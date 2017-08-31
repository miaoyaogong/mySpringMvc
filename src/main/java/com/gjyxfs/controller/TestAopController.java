package com.gjyxfs.controller;

import com.gjyxfs.service.TestAopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 1 on 2017/8/29.
 */

@Controller
public class TestAopController {

    @Autowired
    private TestAopService tas;

    @RequestMapping("/testAop")
    @ResponseBody
    public String testAop(){
       String res =  tas.show();
        return res;
    }
}
