package cn.shuoshuge.dao;


import cn.shuoshuge.entity.User;
import cn.shuoshuge.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class UserDao {

    public void save(User user) {
        String sql = "insert into t_user(username,password,email,phone,avatar,state) value(?,?,?,?,?,?)";
        DbHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),user.getAvatar(),user.getState());
    }

    public void update(User user) {
        String sql = "update t_user set password=?,email=?,avatar=?,state=? where id=?";
        DbHelp.update(sql,user.getPassword(),user.getEmail(),user.getAvatar(),user.getState(),user.getId());
    }

    public User findByUsername(String username) {

        String sql = "select * from t_user where username=?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),username);

    }

    public User findByEmail(String email) {

        String sql = "select * from t_user where email=?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),email);

    }
}
