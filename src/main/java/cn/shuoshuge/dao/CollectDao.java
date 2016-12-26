package cn.shuoshuge.dao;


import cn.shuoshuge.entity.Collect;
import cn.shuoshuge.util.DbHelp;

public class CollectDao {

    public void create(Collect collect) {
        String sql = "insert into t_collect(user_id,topic_id) values(?,?)";
        DbHelp.update(sql,collect.getUser_id(),collect.getTopic_id());
    }

    public void remove(Integer topic_id, Integer user_id) {
        String sql = "delete from t_collect where user_id=? and topic_id=?";
        DbHelp.update(sql,user_id,topic_id);

    }
}
