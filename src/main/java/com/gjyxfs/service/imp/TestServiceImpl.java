package com.gjyxfs.service.imp;

import com.gjyxfs.service.TestService;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 1 on 2017/8/17.
 */
@Service
public class TestServiceImpl implements TestService{
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String getNowTime(){
        return  format.format(new Date());
    }
}
