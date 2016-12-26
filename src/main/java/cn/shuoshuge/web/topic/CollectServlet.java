package cn.shuoshuge.web.topic;


import cn.shuoshuge.dto.JsonResult;
import cn.shuoshuge.entity.Topic;
import cn.shuoshuge.entity.User;
import cn.shuoshuge.exception.ServiceException;
import cn.shuoshuge.service.TopicService;
import cn.shuoshuge.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/collect")
public class CollectServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String topic_id = req.getParameter("topic_id");
        String action = req.getParameter("action");
        User user = getSessionUser(req);
        TopicService service = new TopicService();
        Topic topic = service.findById(topic_id);
        JsonResult jsonResult;

        try {
            int collect_num = service.collect_event(user,topic,action);
            jsonResult = new JsonResult(collect_num);
        } catch (ServiceException e) {
            jsonResult = new JsonResult(e.getMessage());
        }
        getJson(jsonResult,resp);


    }
}
