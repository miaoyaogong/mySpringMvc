package com.gjyxfs.service.imp;

import com.gjyxfs.dao.DataMapper;
import com.gjyxfs.model.Data;
import com.gjyxfs.service.ParsePkData;
import com.gjyxfs.util.FileUtil;
import com.gjyxfs.util.JsonMapper;
import com.gjyxfs.util.POIExcelUtil;
import com.gjyxfs.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 1 on 2017/9/7.
 */
@Service
public class ParsePkDataImpl implements ParsePkData {

    @Autowired
    private DataMapper dataMapper;

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

        } catch (IOException e) {
            return "false";
        }
        return "succese";
    }
}
