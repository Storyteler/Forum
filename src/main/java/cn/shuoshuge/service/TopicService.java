package cn.shuoshuge.service;


import cn.shuoshuge.dao.*;
import cn.shuoshuge.entity.*;
import cn.shuoshuge.exception.ServiceException;
import cn.shuoshuge.util.Page;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class TopicService {

    TopicDao topicDao = new TopicDao();
    NodeDao nodeDao = new NodeDao();
    UserDao userDao = new UserDao();
    ReplyDao replyDao = new ReplyDao();
    CollectDao collectDao = new CollectDao();
    NodifyDao nodifyDao = new NodifyDao();

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

                //创建提醒消息
                if (!user.getId().equals(topic.getUser_id())) {
                    Nodify nodify = new Nodify();
                    nodify.setUser_id(topic.getUser_id());
                    nodify.setContent("<a href=\"/topic?topic_id=" + topic_id + "\">" + topic.getTitle() + "</a>有了新的消息，请查看");
                    nodifyDao.save(nodify);
                }

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

    public Page<Topic> findAll(int pageNo, String node_id) {
        int count;
        if (StringUtils.isEmpty(node_id)) {
            count = topicDao.count();
        } else {
            count = topicDao.count(node_id);
        }
        Page<Topic> topicPage = new Page<>(count,pageNo);
        List<Topic> topicList = topicDao.findAll(node_id,topicPage.getStart(),topicPage.getPageSize());
        topicPage.setItems(topicList);
        return topicPage;

    }

    public List<Node> findAllNode() {
        return nodeDao.findAll();
    }
}
