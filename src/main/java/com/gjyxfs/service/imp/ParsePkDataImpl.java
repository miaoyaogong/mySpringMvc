package com.gjyxfs.service.imp;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gjyxfs.dao.DataMapper;
import com.gjyxfs.model.Data;
import com.gjyxfs.service.ParsePkData;
import com.gjyxfs.util.FileUtil;
import com.gjyxfs.util.JsonMapper;
import com.gjyxfs.util.POIExcelUtil;
import com.gjyxfs.util.PathUtil;
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
            Map<String, List<String>> map= POIExcelUtil.readXlsx(paths.get(0));
            if(map == null || map.size() == 0){
                return "false";
            }
            String value = JsonMapper.getInstance().toJson(map);
            Data data = new Data();
            data.setName("data");
            data.setValue(value);
            dataMapper.insert(data);

            setJsonData(value);

        } catch (IOException e) {
            return "false";
        }
        return "succese";
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
