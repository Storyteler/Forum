package cn.shuoshuge.web;

import com.google.gson.Gson;

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

    public void getPrint(String print,HttpServletResponse response) throws IOException {
        //输出的时候都得写请求头,text/plain为字符串型
        response.setContentType("text/plain;charset=UTF-8");
        //为什么不选择抛出一场
        PrintWriter pw = null;
        pw = response.getWriter();
        pw.print(print);
        pw.flush();
        pw.close();
    }

    public void getJson(Object object,HttpServletResponse response) throws IOException {
        //json请求头为application/json
        response.setContentType("application/json;charest=UTF-8");
        //为什么不选择抛出一场
        PrintWriter pw = null;
        pw = response.getWriter();
        //调用Gson将一个object对象转换为json对象
        pw.print(new Gson().toJson(object));
        pw.flush();
        pw.close();
    }
}
