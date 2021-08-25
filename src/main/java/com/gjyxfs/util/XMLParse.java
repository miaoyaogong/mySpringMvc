package com.gjyxfs.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gjyxfs.model.TulingRes;
import com.gjyxfs.util.orc.WebOCR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XMLParse class
 *
 * 提供提取消息格式中的密文及生成回复消息格式的接口.
 */
public class XMLParse {
    private static Logger logger = LoggerFactory.getLogger(XMLParse.class);
    public static final String[] filter ={"近卫军团","暗夜精灵","100%","天灾军团","不死族","队伍2","队伍1","打开"};
    public static final String filterPath = "/home/ubuntu/tools/site/filter.txt";

    public static final String userBase ="http://users.09game.com/home/GetUserPub";
    public static final String userScore ="http://score.09game.com/Ordinary/SeasonSummary";
    /**
     * 提取出xml数据包中的加密消息
     * @param xmltext 待提取的xml字符串
     * @return 提取出的加密消息字符串
     * @throws AesException
     */
    public static Object[] extract(String xmltext) throws AesException     {
        Object[] result = new Object[3];
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(xmltext);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);

            Element root = document.getDocumentElement();
            NodeList nodelist1 = root.getElementsByTagName("Encrypt");
            NodeList nodelist2 = root.getElementsByTagName("ToUserName");
            //公众平台使用ToUserName，第三方平台使用 AppId
            if(nodelist2 == null || nodelist2.item(0) == null){
                nodelist2 = root.getElementsByTagName("AppId");
            }
            result[0] = 0;
            result[1] = nodelist1.item(0).getTextContent();
            result[2] = nodelist2.item(0).getTextContent();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.ParseXmlError);
        }
    }


    public static String format = "<xml>\n" + "<ToUserName><![CDATA[%1$s]]></ToUserName>\n"
            + "<FromUserName><![CDATA[%2$s]]></FromUserName>\n"
            + "<CreateTime>%3$s</CreateTime>\n"
            + "<MsgType><![CDATA[%4$s]]></MsgType>\n"
            + "<Content><![CDATA[%5$s]]></Content>\n" + "</xml>";

    public static String generateText(String fromUserName, String toUserName, String timestamp, String msgType, String content) {
        Map<String, String> param = new HashMap();
        param.put("key", "526ee23d709a4ff6b97ff464c2cf1d9d");
        param.put("info", content);
        param.put("userid", fromUserName);
        String res = HttpUtils.getData("http://www.tuling123.com/openapi/api", param);
        logger.info("tuling req:{},res:{}", JSONObject.toJSONString(param), res);
        ObjectMapper mapper = new ObjectMapper();
        TulingRes tuling = null;
        try {
            tuling = mapper.readValue(res, TulingRes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(format, fromUserName, toUserName, timestamp, "text", tuling.getText());

    }

    public static String generateSubscribe(String fromUserName, String toUserName, String timestamp, String msgType, String content) {

        return String.format(format, fromUserName, toUserName, timestamp, "text", "欢迎，来到Dota1之家，进开黑群加微信:yihua_888888");

    }

    public static String generateImage(String fromUserName, String toUserName, String timestamp, String msgType, String url) {
        String res = "";
        String content = null;
        try {
            if(CacheUtil.getValue(url) != null){
                res = String.valueOf(CacheUtil.getValue(url));
                return String.format(format, fromUserName, toUserName, timestamp, "text", res);
            }
            content = WebOCR.getContent(url);
            logger.info("WebOCR.getContent url:{}, res:{}", url, content);
            if(!StringUtils.isEmpty(content)){
                Map<String, List<String>> map = resolve(content);
                for(String key : map.keySet()){
                    res = res + key + "：\n";
                    List<String> list = map.get(key);
                    if(!CollectionUtils.isEmpty(list)){
                        for(String word : list){
                            Map<String, String> params1 = new HashMap<>();
                            params1.put("user_name", "'"+word+"'");
                            String userBaseData = HttpUtils.getData(userBase, params1);
                            logger.info("09 userBaseData req:{},res:{}", JSONObject.toJSONString(params1), userBaseData);
                            if(!StringUtils.isEmpty(userBaseData) && userBaseData.contains("\"result\":0")){
                                JSONObject jsonObject = JSONObject.parseObject(userBaseData).getJSONArray("temp").getJSONObject(0);
                                String level = jsonObject.getString("level");
                                String user_id = jsonObject.getString("user_id");
                                Map<String, String> params2 = new HashMap<>();
                                params2.put("UserID", user_id);
                                params2.put("GameTypeID", "1");
                                String userScoreData = HttpUtils.getData(userScore,params2);
                                logger.info("09 userScore req:{},res:{}", JSONObject.toJSONString(params2), userScoreData);
                                JSONArray jsonArray = JSONObject.parseObject(userScoreData).getJSONObject("data").getJSONArray("total");
                                int total_times = 0;
                                int total_win = 0;
                                int total_lost = 0;
                                if(jsonArray.size() > 0){
                                    JSONObject json = jsonArray.getJSONObject(0);
                                    total_times = json.getIntValue("total_times");
                                    total_win = json.getIntValue("total_win");
                                    total_lost = json.getIntValue("total_lost");
                                }
                                DecimalFormat df = new DecimalFormat("0.00");
                                String sl = "0";
                                if(total_times != 0){
                                    sl = df.format((float)total_win/total_times).replace("0.","");
                                }

                                res = res + word + ":" + "等级" + level + "," + "胜:" + total_win +"," + "败:"+total_lost+"," + "胜率:"+ sl + "\n";
                            }
                        }
                    }
                }

                CacheUtil.addTempCache(url, res , 60);
            }
        } catch (Exception e) {
            logger.info("generateImage error", e);
            e.printStackTrace();
            res = "服务繁忙，请稍后再试！";
        }

        return String.format(format, fromUserName, toUserName, timestamp, "text", res);
    }

    public static Map<String,List<String>> resolve(String content) {
        Map<String,List<String>> map = new HashMap();
        JSONObject jsonpObject = JSONObject.parseObject(content);
        JSONArray lines = jsonpObject.getJSONObject("data").getJSONArray("block").getJSONObject(0).getJSONArray("line");
        List<String> jinweiPerson = new ArrayList<>();
        List<String> tianzaiPerson = new ArrayList<>();
        boolean isJinwei = true;
        for(int i = 0; i< lines.size(); i++){
            String word = lines.getJSONObject(i).getJSONArray("word").getJSONObject(0).getString("content");
            word = word.replace("…","灬").replace("、","丶");
            if(word.equals("天灾军团") || word.equals("队伍2")){
                isJinwei =false;
            }
            if(checkFilter(word)){
                if(isJinwei){
                    jinweiPerson.add(word);
                }else {
                    tianzaiPerson.add(word);
                }
            }
        }

        map.put("近卫军团", jinweiPerson);
        map.put("天灾军团", tianzaiPerson);
        String res = "";
        for(String key : map.keySet()){
            res = res + key + "：\n";
            List<String> list = map.get(key);
            if(!CollectionUtils.isEmpty(list)){
                for(String word : list){
                    Map<String, String> params1 = new HashMap<>();
                    params1.put("user_name", "'"+word+"'");
                    String userBaseData = HttpUtils.getData(userBase, params1);
                    if(!StringUtils.isEmpty(userBaseData) && userBaseData.contains("\"result\":0")){
                        JSONObject jsonObject = JSONObject.parseObject(userBaseData).getJSONArray("temp").getJSONObject(0);
                        String level = jsonObject.getString("level");
                        String user_id = jsonObject.getString("user_id");
                        Map<String, String> params2 = new HashMap<>();
                        params2.put("UserID", user_id);
                        params2.put("GameTypeID", "1");
                        String userScoreData = HttpUtils.getData(userScore,params2);
                        JSONArray jsonArray = JSONObject.parseObject(userScoreData).getJSONObject("data").getJSONArray("total");
                        int total_times = 0;
                        int total_win = 0;
                        int total_lost = 0;
                        if(jsonArray.size() > 0){
                            JSONObject json = jsonArray.getJSONObject(0);
                            total_times = json.getIntValue("total_times");
                            total_win = json.getIntValue("total_win");
                            total_lost = json.getIntValue("total_lost");
                        }
                        DecimalFormat df = new DecimalFormat("0.00");
                        String sl = "0";
                        if(total_times != 0){
                            sl = df.format((float)total_win/total_times).replace("0.","");
                        }




                        res = res + word + ":" + "等级" + level + "," + "胜：" + total_win +"," + "败："+total_lost+"," + "胜率："+ sl + "\n";
                    }
                }
            }
        }

        System.out.println(res);
        return map;
    }

    private static boolean checkFilter(String word) {
        for(int j = 0; j < filter.length; j++){
            if(word.contains(filter[j])){
                return false;
            }
        }

//        for(int j = 0; j < filter.length; j++){
//            if(word.contains(filter[j])){
//                return false;
//            }
//        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        resolve("");

    }
}
