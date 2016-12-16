package cn.shuoshuge.web.validate;


import cn.shuoshuge.service.UserService;
import cn.shuoshuge.util.StringUtil;
import cn.shuoshuge.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/validate/username")
public class ValidateUsernameServlet extends BaseServlet {

    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        //处理url中出现的乱码问题
        StringUtil.judgeUtf8(username);
        //判断用户名是否已存在，或是否含有敏感字符
        boolean result = userService.judgeUsername(username);
        if(result) {
            getPrint("true",resp);
        } else {
            getPrint("false",resp);
        }


    }
}
