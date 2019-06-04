package com.gjyxfs.dao;

import com.gjyxfs.model.SimpleUserInfo;


public interface SimpleUserInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SimpleUserInfo record);

    int insertSelective(SimpleUserInfo record);

    SimpleUserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SimpleUserInfo record);

    int updateByPrimaryKey(SimpleUserInfo record);
}