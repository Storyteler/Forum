package cn.shuoshuge.dao;


import cn.shuoshuge.entity.User;
import cn.shuoshuge.util.DbHelp;
import com.sun.org.apache.xerces.internal.impl.dv.DVFactoryException;
import org.apache.commons.dbutils.handlers.BeanHandler;

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

    public User findByUsername(String username) {

        String sql = "select * from t_user where username=?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),username);

    }

    public User findByEmail(String email) {

        String sql = "select from t_user where email=?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),email);

    }
}