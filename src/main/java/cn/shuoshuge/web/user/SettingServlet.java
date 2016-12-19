package cn.shuoshuge.web.user;


import cn.shuoshuge.dto.JsonResult;
import cn.shuoshuge.entity.User;
import cn.shuoshuge.exception.ServiceException;
import cn.shuoshuge.service.UserService;
import cn.shuoshuge.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/setting")
public class SettingServlet extends BaseServlet {

    public UserService userService = new UserService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jumpToJsp("setting",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("email".equals(action)) {
            settingEmail(req,resp);
        } else if("password".equals(action)) {
            settingPassword(req,resp);
        }
    }

    private void settingPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String password = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        User user = getSessionUser(req);
        JsonResult jsonResult;
        try {
            userService.updatePassword(user,password,newPassword);
            jsonResult = new JsonResult();
            jsonResult.setState("success");
        } catch (ServiceException e) {
            jsonResult = new JsonResult(e.getMessage());
        }
        getJson(jsonResult,resp);


    }

    private void settingEmail(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String newEmail = req.getParameter("email");
        User user = getSessionUser(req);
        userService.updateEmail(user,newEmail);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setState("success");
        getJson(jsonResult,resp);
    }
}
