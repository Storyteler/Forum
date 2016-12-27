package cn.shuoshuge.web.user;


import cn.shuoshuge.entity.Nodify;
import cn.shuoshuge.entity.User;
import cn.shuoshuge.service.UserService;
import cn.shuoshuge.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/nodify")
public class NodifyServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService service = new UserService();
        User user = getSessionUser(req);
        List<Nodify> list = service.findNodifyByUser(user);
        req.setAttribute("list",list);
        jumpToJsp("user/nodify",req,resp);
    }
}
