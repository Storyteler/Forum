package cn.shuoshuge.web.topic;


import cn.shuoshuge.dao.NodeDao;
import cn.shuoshuge.dao.ReplyDao;
import cn.shuoshuge.dto.JsonResult;
import cn.shuoshuge.entity.Node;
import cn.shuoshuge.entity.Reply;
import cn.shuoshuge.entity.Topic;
import cn.shuoshuge.exception.ServiceException;
import cn.shuoshuge.service.TopicService;
import cn.shuoshuge.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/edit")
public class EditServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topic_id = req.getParameter("topic_id");
        TopicService service = new TopicService();
        try {
            Topic topic = service.findById(topic_id);
            NodeDao dao = new NodeDao();
            List<Node> list = dao.findAll();

            req.setAttribute("list",list);
            req.setAttribute("topic",topic);
            jumpToJsp("topic/edit",req,resp);
        } catch (ServiceException e) {
            resp.sendError(404,e.getMessage());
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String nodeName = req.getParameter("nodename");
        String topic_id = req.getParameter("topic_id");
        TopicService service = new TopicService();
        JsonResult jsonResult;

        try {
            Topic topic = service.edit_topic(title,content,nodeName,topic_id);
            jsonResult = new JsonResult(topic);
        } catch (ServiceException e) {
            jsonResult = new JsonResult(e.getMessage());
        }
        getJson(jsonResult,resp);
    }
}
