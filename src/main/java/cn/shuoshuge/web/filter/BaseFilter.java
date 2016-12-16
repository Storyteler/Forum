package cn.shuoshuge.web.filter;


import javax.servlet.*;
import java.io.IOException;

public abstract class BaseFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public abstract void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain);

    @Override
    public void destroy() {

    }
}
