package cn.shuoshuge.web.user;


import cn.shuoshuge.service.UserService;
import cn.shuoshuge.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/nodify_read")
public class NodifyReadServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        UserService service = new UserService();
        service.updateNodify(ids);
        getPrint("success",resp);
    }
}
