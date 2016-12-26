package cn.shuoshuge.service;


import cn.shuoshuge.dao.*;
import cn.shuoshuge.entity.*;
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
        Integer node_id = Integer.valueOf(nodeId);
        Topic topic = new Topic();
        topic.setUser_id(user.getId());
        topic.setTitle(title);
        topic.setNode_id(node_id);
        topic.setContent(content);
        topic.setLast_replytime(new Timestamp(new Date().getTime()));
        Integer id = topicDao.save(topic);
        Node node = nodeDao.findNodeById(node_id);
        node.setTopic_num(node.getTopic_num() + 1);
        nodeDao.update(node);
        topic.setId(id);

        return topic;
    }

    public Topic findById(String id) {
        if (id != null) {
            Topic topic = topicDao.query(Integer.valueOf(id));
            topic.setUser(userDao.findById(topic.getUser_id()));
            topic.setNode(nodeDao.findNodeById(topic.getNode_id()));
            return topic;
        } else {
            throw new ServiceException("找不到帖子");
        }

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

    public Topic edit_topic(String title, String content, String nodeName,String topic_id) {
        Node afterNode = nodeDao.findNodeById(Integer.valueOf(nodeName));
        if (afterNode != null) {
            Topic topic = findById(topic_id);
            if (topic != null) {
                Node beforeNode = nodeDao.findNodeById(topic.getNode_id());
                beforeNode.setTopic_num(beforeNode.getTopic_num() - 1);
                nodeDao.update(beforeNode);

                topic.setNode_id(afterNode.getId());
                topic.setTitle(title);
                topic.setContent(content);
                topicDao.update(topic);

                afterNode.setTopic_num(afterNode.getTopic_num() + 1);
                nodeDao.update(afterNode);

                return topic;
            } else {
                throw new ServiceException("该贴不存在或已删除");
            }
        } else {
            throw new ServiceException("该节点不存在");
        }


    }
}
