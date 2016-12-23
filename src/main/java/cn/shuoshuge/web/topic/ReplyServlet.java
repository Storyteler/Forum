package cn.shuoshuge.web.topic;

import cn.shuoshuge.entity.User;
import cn.shuoshuge.service.TopicService;
import cn.shuoshuge.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reply")
public class ReplyServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topic_id = req.getParameter("topic_id");
        String content = req.getParameter("content");
        User user = getSessionUser(req);

        TopicService service = new TopicService();
        try{
            service.create_reply(topic_id,content,user);
            resp.sendRedirect("/topic?topic_id=" + topic_id);
        } catch (Exception e) {
            resp.sendError(404,e.getMessage());
        }

    }
}
