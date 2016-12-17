package cn.shuoshuge.web.user;

import cn.shuoshuge.service.UserService;
import cn.shuoshuge.web.BaseServlet;
import com.google.common.collect.Maps;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/found")
public class FoundPasswordServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jumpToJsp("foundPassword",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String value = req.getParameter("value");
        String type = req.getParameter("type");

        Map<String ,Object> map = Maps.newHashMap();
        UserService userService = new UserService();
        String sessionID = req.getSession().getId();

        try {
            userService.foundPassword(value,type,sessionID);
            map.put("state","success");
        } catch (Exception e) {
            map.put("state","error");
            map.put("message",e.getMessage());
        }
        getJson(map,resp);
    }
}
