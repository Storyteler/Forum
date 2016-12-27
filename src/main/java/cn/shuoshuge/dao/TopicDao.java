package cn.shuoshuge.dao;


import cn.shuoshuge.entity.Reply;
import cn.shuoshuge.entity.Topic;
import cn.shuoshuge.entity.User;
import cn.shuoshuge.util.DbHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicDao {
    public Integer save(Topic topic) {

        String sql = "insert into t_topic(user_id,node_id,title,content) values(?,?,?,?)";
        return DbHelp.insert(sql,topic.getUser_id(),topic.getNode_id(),topic.getTitle(),topic.getContent());

    }

    public Topic query(Integer id) {

        String sql = "select * from t_topic where id=?";
        return DbHelp.query(sql,new BeanHandler<>(Topic.class),id);
    }


    public void update(Topic topic) {

        String sql = "update t_topic set title=?,content=?,click_num=?,collect_num=?,thanks_num=?,reply_num=?,last_replytime=?,node_id=? where id=?";
        DbHelp.update(sql,topic.getTitle(),topic.getContent(),topic.getClick_num(),topic.getCollect_num(),topic.getThanks_num(),topic.getReply_num(),topic.getLast_replytime(),topic.getNode_id(),topic.getId());

    }

    public List<Topic> findAll(String node_id, int start, int pageSize) {
        String sql = "select tu.username,tu.avatar,tt.* from t_user tu,t_topic tt where tu.id=tt.user_id";
        List<Object> array = new ArrayList<>();
        String where = "";
        if (StringUtils.isNotEmpty(node_id)) {
            where += " and node_id=?";
            array.add(node_id);
        }
        where += " order by tt.last_replytime desc limit ?,?";
        array.add(start);
        array.add(pageSize);
        sql += where;

        return DbHelp.query(sql, new AbstractListHandler<Topic>() {
            @Override
            protected Topic handleRow(ResultSet resultSet) throws SQLException {
                Topic topic = new BasicRowProcessor().toBean(resultSet,Topic.class);
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setAvatar(resultSet.getString("avatar"));
                topic.setUser(user);
                return topic;
            }
        },array.toArray());

    }

    public int count() {
        String sql = "select count(*) from t_topic";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public int count(String node_id) {
        String sql = "select count(*) from t_topic where node_id=?";
        return DbHelp.query(sql,new ScalarHandler<Long>(),node_id).intValue();
    }
}
