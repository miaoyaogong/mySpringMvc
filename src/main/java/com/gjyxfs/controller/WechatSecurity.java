package com.gjyxfs.controller;

import com.alibaba.fastjson.JSONObject;
import com.gjyxfs.util.MessageUtil;
import com.gjyxfs.util.SignUtil;
import com.gjyxfs.util.XMLParse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by leon on 8/2/17.
 */
@Controller
public class WechatSecurity {

    private static Logger logger = LoggerFactory.getLogger(WechatSecurity.class);

    /**
     * @Description: 用于接收 get 参数，返回验证参数
     * @param request
     * @param response
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     */
    @RequestMapping(value = "security", produces="application/json;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    public String doGet(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "signature", required = false) String signature,
            @RequestParam(value = "timestamp", required = false) String timestamp,
            @RequestParam(value = "nonce", required = false) String nonce,
            @RequestParam(value = "echostr", required = false) String echostr) {

        logger.info("signature :{}, timestamp : {}, nonce : {} ,echostr : {}"
                , signature, timestamp, nonce, echostr);
        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                return echostr;
            } else {
                logger.info("这里存在非法请求！");
            }
        } catch (Exception e) {
            logger.error("非法请求");
        }
        return "不合法请求";
    }

    @RequestMapping(value = "security", produces="application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    // post 方法用于接收微信服务端消息
    public String doPost(HttpServletRequest request,HttpServletResponse response) {
        try{
            Map<String, String> map= MessageUtil.parseXml(request);
            logger.info("WeChat request :{}", JSONObject.toJSONString(map));

            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String picUrl = map.get("PicUrl");
            String event = map.get("Event");

            String res = "";
            if("text".equals(msgType)){
                res = XMLParse.generateText(fromUserName, toUserName, String.valueOf(new Date().getTime()), msgType, content);
            }else if("image".equals(msgType)){
                res = XMLParse.generateImage(fromUserName, toUserName, String.valueOf(new Date().getTime()), msgType, picUrl);
            }else if("event".equals(msgType) && event.equals("subscribe") ){
                res = XMLParse.generateSubscribe(fromUserName, toUserName, String.valueOf(new Date().getTime()), msgType, picUrl);
            }
            return res;
        }catch(Exception e){
            logger.error("解析xml失败");
        }
        return  null;
    }
}
