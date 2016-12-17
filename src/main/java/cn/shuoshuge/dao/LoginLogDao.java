package cn.shuoshuge.dao;

import cn.shuoshuge.entity.LoginLog;
import cn.shuoshuge.util.DbHelp;

public class LoginLogDao {
    public void save(LoginLog loginLog) {

        String sql = "insert into t_login_log(ip,user_id) value(?,?)";
        DbHelp.update(sql,loginLog.getIp(),loginLog.getUser_id());

    }
}
