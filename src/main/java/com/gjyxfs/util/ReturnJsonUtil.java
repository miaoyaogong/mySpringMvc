package com.gjyxfs.util;


import java.util.Map;

public class ReturnJsonUtil {


    public static String successStr(String jsonStr) {
        String resultStr = "";
        if (jsonStr == "") {
            resultStr = "{\"code\":100,\"message\":\"\",\"result\":\"\"}";
        } else {
            resultStr = "{\"code\":100,\"message\":\"\",\"result\":\"" + jsonStr + "\"}";
        }

        return resultStr;
    }


    public static String errorStr(String errCode, String jsonStr) {
        String resultStr = "";
        if (jsonStr == "") {
            resultStr = "{\"code\":" + errCode + ",\"message\":\"\",\"result\":\"\"}";
        } else {
            resultStr = "{\"code\":" + errCode + ",\"message\":" + jsonStr + ",\"result\":\"\"}";
        }
        return resultStr;
    }

}
