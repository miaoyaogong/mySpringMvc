package com.gjyxfs.dao;

import com.gjyxfs.model.Data;

public interface DataMapper {
    int deleteByPrimaryKey(String name);

    int insert(Data record);

    int insertSelective(Data record);

    Data selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(Data record);

    int updateByPrimaryKeyWithBLOBs(Data record);
}