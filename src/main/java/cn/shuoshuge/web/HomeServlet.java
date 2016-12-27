package cn.shuoshuge.web;

import cn.shuoshuge.dao.TopicDao;
import cn.shuoshuge.entity.Node;
import cn.shuoshuge.entity.Topic;
import cn.shuoshuge.service.TopicService;
import cn.shuoshuge.util.Page;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String p = req.getParameter("p");
        String node_id = req.getParameter("node_id");
        int pageNo = p == null ? 1 : Integer.valueOf(p);
        if (node_id != null && !StringUtils.isNumeric(node_id)) {
            node_id="";
        }

        TopicService service = new TopicService();
        List<Node> nodeList = service.findAllNode();
        Page<Topic> page = service.findAll(pageNo,node_id);

        req.setAttribute("nodeList",nodeList);
        req.setAttribute("page",page);
        jumpToJsp("home",req,resp);

    }
}
