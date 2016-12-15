package cn.shuoshuge.dao;


import cn.shuoshuge.entity.User;
import cn.shuoshuge.util.DbHelp;

public class UserDao {

    public void save(User user) {
        String sql = "insert into t_user(username,password,email,phone,state,) value(?,?,?,?,?)";
        DbHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),user.getState());
    }

    public void updateEmail(User user) {
        String sql = "update t_user email=? where id=?";
        DbHelp.update(sql,user.getEmail(),user.getId());
    }

    public void updatePassword(User user) {
        String sql = "update t_user password=? where id=?";
        DbHelp.update(sql,user.getPassword(),user.getId());
    }

    public void updateAvatar(User user) {
        String sql = "update t_user avater=? where id=?";
        DbHelp.update(sql,user.getAvatar(),user.getId());
    }

}
