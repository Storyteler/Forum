package cn.shuoshuge.web.user;

import cn.shuoshuge.entity.User;
import cn.shuoshuge.service.UserService;
import cn.shuoshuge.web.BaseServlet;
import com.google.common.collect.Maps;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jumpToJsp("login",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String ip = req.getRemoteAddr();

        UserService userService = new UserService();
        Map<String,Object> map = Maps.newHashMap();
        try {
            User user = userService.validateUser(username, password,ip);
            map.put("state","success");
        } catch (Exception ex) {
            String message = ex.getMessage();
            map.put("state","error");
            map.put("message",message);
        }
        getJson(map,resp);
    }
}
