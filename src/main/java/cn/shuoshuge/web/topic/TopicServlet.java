package cn.shuoshuge.web.topic;

import cn.shuoshuge.entity.Reply;
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

@WebServlet("/topic")
public class TopicServlet extends BaseServlet {

    TopicService topicService = new TopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        User user = (User) req.getSession().getAttribute("user");
        try {
            Topic topic = topicService.findById(id);
            List<Reply> list = topicService.finAllReply(id);

            req.setAttribute("list",list);
            req.setAttribute("topic", topic);
            jumpToJsp("topic/topic", req, resp);
        } catch (Exception e) {
            resp.sendError(404,e.getMessage());
        }
    }


}
