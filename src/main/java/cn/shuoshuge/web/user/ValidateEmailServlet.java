package cn.shuoshuge.web.user;

import cn.shuoshuge.entity.User;
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
        String type = req.getParameter("type");
        String email = req.getParameter("email");
        User user = getSessionUser(req);
        UserService userService = new UserService();

        if (type != null) {
            if(user != null) {
                if (user.getEmail().equals(email)) {
                    getPrint("true",resp);
                    return;
                }
            } else {
                getPrint("false",resp);
            }
        }
        boolean result = userService.judgeEmail(email);
        if(result) {
            getPrint("true",resp);
        } else {
            getPrint("false",resp);
        }
    }

}
