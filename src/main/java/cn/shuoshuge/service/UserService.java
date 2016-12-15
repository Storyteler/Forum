package cn.shuoshuge.service;

import cn.shuoshuge.dao.UserDao;
import cn.shuoshuge.entity.User;
import cn.shuoshuge.util.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {

    UserDao userDao = new UserDao();

    public boolean judgeUsername(String username) {
//得到config中username键的值
        String name = Config.get(username);
        List<String> list = Arrays.asList(name.split(","));
        User user = userDao.findByUsername(username);
        if(user == null) {
            return true;
        } else if (list.contains(username)) {
            return false;
        }
            return false;
    }


    public boolean judgeEmail(String email) {

        User user = userDao.findByEmail(email);
        if(user == null) {
            return true;
        } else {
            return false;
        }

    }
}
