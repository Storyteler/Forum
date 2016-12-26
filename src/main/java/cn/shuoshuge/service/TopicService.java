package cn.shuoshuge.service;


import cn.shuoshuge.dao.*;
import cn.shuoshuge.entity.Collect;
import cn.shuoshuge.entity.Reply;
import cn.shuoshuge.entity.Topic;
import cn.shuoshuge.entity.User;
import cn.shuoshuge.exception.ServiceException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class TopicService {

    TopicDao topicDao = new TopicDao();
    NodeDao nodeDao = new NodeDao();
    UserDao userDao = new UserDao();
    ReplyDao replyDao = new ReplyDao();
    CollectDao collectDao = new CollectDao();

    public Topic createNewTopic(User user, String title, String nodeId, String content) {
        Topic topic = new Topic();
        topic.setUser_id(user.getId());
        topic.setTitle(title);
        topic.setNode_id(Integer.valueOf(nodeId));
        topic.setContent(content);
        Integer id = topicDao.save(topic);
        topic.setId(id);

        return topic;
    }

    public Topic findById(String id) {

        Topic topic = topicDao.query(Integer.valueOf(id));
        topic.setUser(userDao.findById(topic.getUser_id()));
        topic.setNode(nodeDao.findNodeById(topic.getNode_id()));
        return topic;
    }

    public void create_reply(String topic_id, String content, User user) {
        Reply reply = new Reply();
        Topic topic = topicDao.query(Integer.valueOf(topic_id));

        if (user != null) {
            if (topic != null) {
                reply.setTopic_id(Integer.valueOf(topic_id));
                reply.setContent(content);
                reply.setUser_id(user.getId());
                replyDao.create_reply(reply);

                topic.setReply_num(topic.getReply_num() + 1);
                topic.setLast_replytime(new Timestamp(new Date().getTime()));
                topicDao.update(topic);
            } else {
                throw new ServiceException("该贴不存在或已删除");
            }
        } else {
            throw new ServiceException("用户不存在");
        }

    }

    public List<Reply> finAllReply(String id) {
        return replyDao.findAll(id);
    }

    public void update_topic(Topic topic) {

        topic.setClick_num(topic.getClick_num() + 1);
        topicDao.update(topic);

    }

    public int collect_event(User user, Topic topic, String action) {

        if ("collect".equals(action)) {
            return create_collect(user,topic);
        } else {
            return remove_collect(user,topic);
        }

    }

    private int remove_collect(User user, Topic topic) {
        Integer topic_id = topic.getId();
        Integer user_id = user.getId();

        collectDao.remove(topic_id,user_id);
        topic.setCollect_num(topic.getCollect_num() - 1);
        topicDao.update(topic);
        return topic.getCollect_num();
    }

    private int create_collect(User user, Topic topic) {

        Integer user_id = user.getId();
        Integer topic_id = topic.getId();
        Collect collect = new Collect();
        collect.setUser_id(user_id);
        collect.setTopic_id(topic_id);

        collectDao.create(collect);
        topic.setCollect_num(topic.getCollect_num() + 1);
        topicDao.update(topic);
        return topic.getCollect_num();
    }
}
