package cn.shuoshuge.web.user;

import cn.shuoshuge.service.UserService;
import cn.shuoshuge.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/email")
public class ValidateEmailServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        UserService userService = new UserService();
        boolean result = userService.judgeEmail(email);
        if(result) {
            getPrint("true",resp);
        } else {
            getPrint("false",resp);
        }
    }
}
