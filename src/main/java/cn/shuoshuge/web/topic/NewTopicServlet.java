package cn.shuoshuge.web.topic;

import cn.shuoshuge.dao.NodeDao;
import cn.shuoshuge.dto.JsonResult;
import cn.shuoshuge.entity.Node;
import cn.shuoshuge.entity.Topic;
import cn.shuoshuge.entity.User;
import cn.shuoshuge.service.TopicService;
import cn.shuoshuge.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/newTopic")
public class NewTopicServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NodeDao dao = new NodeDao();
        List<Node> list = dao.findAll();
        req.setAttribute("list",list);
        jumpToJsp("topic/newTopic",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String nodeId = req.getParameter("nodeId");
        String content = req.getParameter("content");
        User user = getSessionUser(req);
        TopicService topicService = new TopicService();

        Topic topic = topicService.createNewTopic(user,title,nodeId,content);
        JsonResult jsonResult = new JsonResult(topic);
        getJson(jsonResult,resp);


    }
}
