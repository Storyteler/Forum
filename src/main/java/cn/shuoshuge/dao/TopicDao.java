package cn.shuoshuge.dao;


import cn.shuoshuge.entity.Reply;
import cn.shuoshuge.entity.Topic;
import cn.shuoshuge.entity.User;
import cn.shuoshuge.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

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
}
