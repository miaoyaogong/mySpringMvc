package com.gjyxfs.service.imp;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gjyxfs.dao.DataMapper;
import com.gjyxfs.model.Data;
import com.gjyxfs.service.ParsePkData;
import com.gjyxfs.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by 1 on 2017/9/7.
 */
@Service
public class ParsePkDataImpl implements ParsePkData {

    @Autowired
    private DataMapper dataMapper;

    private String jsonData;

    @Override
    public String parsePkData(HttpServletRequest request) {
        String dcpTempPath = PathUtil.tomcatPath()+"dcpTemp";
        FileUtil.clearFolder(dcpTempPath);
        try {
            List<String> paths = FileUtil.upload(request, dcpTempPath);
            if(paths == null || paths.size() == 0){
                return ReturnJsonUtil.errorStr("10001","请先选择文件");
            }
            Map<String, List<String>> map= POIExcelUtil.readXlsx(paths.get(0));
            if(map == null || map.size() == 0){
                return ReturnJsonUtil.errorStr("10002","文件格式有误");
            }
            String value = JsonMapper.getInstance().toJson(map);
            Data data = new Data();
            data.setName("data");
            data.setValue(value);
            dataMapper.insert(data);

            setJsonData(value);

        } catch (IOException e) {
            return ReturnJsonUtil.errorStr("10003","系统错误");
        }
        return ReturnJsonUtil.successStr("成功");
    }

    @PostConstruct
    public void initData() {
        Data data = dataMapper.selectByPrimaryKey("data");
        if(data == null){
            return;
        }
        setJsonData(data.getValue());
    }

    @Override
    public String getGroupData() {
        if(StringUtils.isEmpty(jsonData)){
            return "";
        }
        Map<String, Object> map =  JsonMapper.getInstance().toMap(jsonData);
        List<String> ls = new ArrayList<>();
        for (String key : map.keySet()){
            ls.add(key);
        }
        Collections.sort(ls);
        return JsonMapper.getInstance().toJson(ls);
    }

    @Override
    public String getMemberData(String group) {
        if(StringUtils.isEmpty(jsonData)){
            return "";
        }
        Map<String, Object> map =  JsonMapper.getInstance().toMap(jsonData);
        Object value = null;
        for (String key : map.keySet()){
            if(key.equals(group)){
                value = map.get(key);
                break;
            }
        }
        if(value != null){
            List<String> ls = (ArrayList<String>)value;
            return JsonMapper.getInstance().toJson(ls);
        }
        return "";
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
