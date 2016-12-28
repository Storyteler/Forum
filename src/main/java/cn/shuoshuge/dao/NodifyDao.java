package cn.shuoshuge.dao;


import cn.shuoshuge.entity.Nodify;
import cn.shuoshuge.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class NodifyDao {


    public void save(Nodify nodify) {
        String sql = "insert into t_nodify(content,user_id) values(?,?)";
        DbHelp.update(sql,nodify.getContent(),nodify.getUser_id());
    }

    public List<Nodify> findByUserId(Integer id) {
        String sql = "select * from t_nodify where user_id=? order by readtime";
        return DbHelp.query(sql,new BeanListHandler<>(Nodify.class),id);
    }

    public Nodify findById(String id) {
        String sql = "select * from t_nodify where id=?";
        return DbHelp.query(sql,new BeanHandler<>(Nodify.class),id);
    }

    public void update(Nodify nodify) {

        String sql = "update t_nodify set content=?,readtime=?,state=? where id=?";
        DbHelp.update(sql,nodify.getContent(),nodify.getReadtime(),nodify.getState(),nodify.getId());

    }
}
