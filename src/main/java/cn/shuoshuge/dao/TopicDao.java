package cn.shuoshuge.dao;


import cn.shuoshuge.entity.Topic;
import cn.shuoshuge.util.DbHelp;

public class TopicDao {
    public void save(Topic topic) {

        String sql = "insert into t_topic(user_id,node_id,title,content) values(?,?,?,?)";
        DbHelp.update(sql,topic.getUser_id(),topic.getNode_id(),topic.getTitle(),topic.getContent());

    }
}
