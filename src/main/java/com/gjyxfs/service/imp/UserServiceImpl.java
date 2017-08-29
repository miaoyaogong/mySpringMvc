package com.gjyxfs.service.imp;

import com.gjyxfs.dao.IUserDAO;
import com.gjyxfs.model.User;
import com.gjyxfs.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 1 on 2017/8/28.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    public List<User> getAllUsers() {
        List<User> users = userDAO.selectAll();
        return users;
    }
}
