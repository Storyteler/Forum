package cn.shuoshuge.service;


import cn.shuoshuge.dao.NodeDao;
import cn.shuoshuge.dao.TopicDao;
import cn.shuoshuge.entity.Topic;
import cn.shuoshuge.entity.User;

public class TopicService {

    TopicDao topicDao = new TopicDao();

    public Topic createNewTopic(User user, String title, String nodeId, String content) {
        Topic topic = new Topic();
        topic.setUser_id(user.getId());
        topic.setTitle(title);
        topic.setNode_id(Integer.valueOf(nodeId));
        topic.setContent(content);
        topicDao.save(topic);

        return topic;
    }
}
