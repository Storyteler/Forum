package cn.shuoshuge.web.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class CharFilter extends BaseFilter {

    private String result = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encoding = filterConfig.getInitParameter("encoding");
        if(StringUtils.isNotEmpty(encoding)) {
            result = encoding;
        }
    }

    @Override
    public void doFilter (ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain){
        //为什么不能抛出异常
        try {
            servletRequest.setCharacterEncoding(result);
            servletResponse.setCharacterEncoding(result);
            filterChain.doFilter(servletRequest,servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
