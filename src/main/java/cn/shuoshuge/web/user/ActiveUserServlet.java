package cn.shuoshuge.web.user;

import cn.shuoshuge.service.UserService;
import cn.shuoshuge.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/active")
public class ActiveUserServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        if(StringUtils.isEmpty(token)) {
            resp.sendError(404);
        } else {
            UserService userService = new UserService();
            try{
                userService.activeUser(token);
                jumpToJsp("user/active_success",req,resp);
            } catch (Exception ex) {
                String message = ex.getMessage();
                req.setAttribute("message",message);
                jumpToJsp("user/active_fail",req,resp);
            }
        }
    }
}
