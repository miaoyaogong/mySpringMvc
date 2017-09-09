package com.gjyxfs.controller;

import com.gjyxfs.service.ParsePkData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 1 on 2017/9/7.
 */
@Controller
public class PkController {

    @Autowired
    private ParsePkData parsePkData;

    @RequestMapping("/")
    public String index(HttpServletRequest request){
        return "pk";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(HttpServletRequest request){
        String res = parsePkData.parsePkData(request);
        return res;
    }

    @RequestMapping(value = "/getGroupData", produces="application/json;charset=utf-8")
    @ResponseBody
    public String getData(){
        String res = parsePkData.getGroupData();
        return res;
    }

    @RequestMapping(value = "/getMemberData", produces="application/json;charset=utf-8")
    @ResponseBody
    public String getMemberData(HttpServletRequest request){
        String group =  request.getParameter("group");
        String res = parsePkData.getMemberData(group);
        return res;
    }
}
