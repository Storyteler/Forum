package cn.shuoshuge.web.user;

import cn.shuoshuge.entity.User;
import cn.shuoshuge.service.UserService;
import cn.shuoshuge.web.BaseServlet;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/reset")
public class ResetServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        if (StringUtils.isNotEmpty(token)) {
            UserService userService = new UserService();
            try {
                User user = userService.foundUser(token);
                req.setAttribute("token",token);
                req.setAttribute("user",user);
                jumpToJsp("user/reset",req,resp);
            } catch (Exception e) {
                req.setAttribute("message",e.getMessage());
                jumpToJsp("user/found_fail",req,resp);
            }
        } else {
            resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String password = req.getParameter("password");

        UserService userService = new UserService();
        Map<String ,Object> map = Maps.newHashMap();

        try{
            userService.reset(token,password);
            map.put("state","success");
        } catch (Exception e) {
            map.put("state","error");
            map.put("message",e.getMessage());
        }
        getJson(map,resp);
    }
}
