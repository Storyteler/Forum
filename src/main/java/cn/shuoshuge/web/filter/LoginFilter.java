package cn.shuoshuge.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginFilter extends BaseFilter {

    private List<String> list;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String accessUri = filterConfig.getInitParameter("accessUri");
        list = Arrays.asList(accessUri.split(","));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        if (list != null && list.contains(uri)) {
            if (request.getSession().getAttribute("user") != null) {
                try {
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    response.sendRedirect("/login?redirect=" + uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
