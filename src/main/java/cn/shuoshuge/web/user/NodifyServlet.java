package cn.shuoshuge.web.user;


import cn.shuoshuge.dto.JsonResult;
import cn.shuoshuge.entity.Nodify;
import cn.shuoshuge.entity.User;
import cn.shuoshuge.service.UserService;
import cn.shuoshuge.web.BaseServlet;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getSessionUser(req);
        //根据guava 的Collections2.filter 过滤未读消息数据
        List<Nodify> notifyList = new UserService().findNodifyByUser(user);
        List<Nodify> unreadList = Lists.newArrayList(Collections2.filter(notifyList, new Predicate<Nodify>() {
            public boolean apply(Nodify notify) {
                return notify.getState() == 0;
            }
        }));
        JsonResult jsonResult = new JsonResult();
        jsonResult.setData(unreadList.size());
        jsonResult.setState("success");
        getJson(jsonResult,resp);
    }
}
