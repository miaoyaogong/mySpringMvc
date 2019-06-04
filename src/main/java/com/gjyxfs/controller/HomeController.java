package com.gjyxfs.controller;

import com.gjyxfs.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    /**
     * 首页
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest req) {
        model.addAttribute("menu","index");
        model.addAttribute("siteName", Constants.siteName);

        return "index";
    }


    /**
     * 服务页面
     */
    @RequestMapping(value = "/service",method = RequestMethod.GET)
    public String service(Model model, String num) {
        model.addAttribute("menu","service");
        model.addAttribute("siteName", Constants.siteName);
        if("1".equals(num)){
            return "service1";
        }else if("2".equals(num)){
            return "service2";
        }else if("3".equals(num)){
            return "service3";
        }else if("4".equals(num)){
            return "service4";
        }
        return "service";
    }

    /**
     * 案例页面
     */
    @RequestMapping(value = "/case",method = RequestMethod.GET)
    public String successCase(Model model, String num) {

        model.addAttribute("menu","case");
        model.addAttribute("siteName", Constants.siteName);
        if("1".equals(num)){
            return "case1";
        }else if("2".equals(num)){
            return "case2";
        }
        return "case";
    }

    /**
     * 吉祥物页面
     */
    @RequestMapping(value = "/treasure",method = RequestMethod.GET)
    public String treasure(Model model) {
        model.addAttribute("menu","treasure");
        model.addAttribute("siteName", Constants.siteName);
        return "treasure";
    }
    /**
     * 关于页面
     */
    @RequestMapping(value = "/about",method = RequestMethod.GET)
    public String about(Model model) {
        model.addAttribute("menu","about");
        model.addAttribute("siteName", Constants.siteName);
        return "about";
    }

}
