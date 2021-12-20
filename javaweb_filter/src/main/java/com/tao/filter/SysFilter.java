package com.tao.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        Object username = request.getSession().getAttribute("username");
        if(username==null){
            response.sendRedirect("/filter/error.jsp");
        }

        System.out.println("无论上面重不重定向，这里都会执行");//重定向不带请求数据，所以下面函数执行应该也不会往哪里传数据了
        filterChain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }
}
