package cn.shuoshuge.web.user;

import cn.shuoshuge.service.UserService;
import cn.shuoshuge.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/reg")
public class RegServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jumpToJsp("reg.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        //json一般传入一个键值对，用Map集合
        Map<String ,String> map = new HashMap<>();
        UserService userService = new UserService();
        try {
            userService.save(username,password,email,phone);
            map.put("state","success");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state","error");
            map.put("message","注册失败，请稍后重试");
        }
        //将map写入json
        getJson(map,resp);

    }
}
