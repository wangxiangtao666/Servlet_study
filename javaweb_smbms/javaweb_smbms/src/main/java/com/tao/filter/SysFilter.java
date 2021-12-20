package com.tao.filter;

import com.tao.pojo.User;
import com.tao.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        if(user!=null){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }
    }

    public void destroy() {

    }
}
