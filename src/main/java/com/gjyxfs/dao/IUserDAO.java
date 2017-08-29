package com.gjyxfs.dao;


import com.gjyxfs.model.User;

import java.util.List;

/**
 * Created by 1 on 2017/8/28.
 */
public interface IUserDAO {

    public List<User> selectAll();
}
