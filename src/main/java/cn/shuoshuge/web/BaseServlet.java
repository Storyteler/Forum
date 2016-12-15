package cn.shuoshuge.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseServlet extends HttpServlet {

    public void jumpToJsp(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/views/" + path).forward(request,response);
    }

    public void getPrint(String print,HttpServletResponse response) {
        //为什么不选择抛出一场
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.print(print);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.flush();
        pw.close();

    }
}
