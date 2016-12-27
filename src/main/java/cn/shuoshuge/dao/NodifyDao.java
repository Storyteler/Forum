package cn.shuoshuge.dao;


import cn.shuoshuge.entity.Nodify;
import cn.shuoshuge.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class NodifyDao {


    public void save(Nodify nodify) {
        String sql = "insert into t_nodify(content,user_id) values(?,?)";
        DbHelp.update(sql,nodify.getContent(),nodify.getUser_id());
    }

    public List<Nodify> findByUserId(Integer id) {
        String sql = "select * from t_nodify where user_id=?";
        return DbHelp.query(sql,new BeanListHandler<>(Nodify.class),id);
    }
}
