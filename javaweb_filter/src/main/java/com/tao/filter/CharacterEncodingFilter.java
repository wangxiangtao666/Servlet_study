package com.tao.filter;


import javax.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {
    @Override
    //初始化，web服务器启动时就已经初始化了过滤器，这个函数是在初始化时可以添加一些操作，初始化后随时等待过滤对象出现。
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CharacterEncodingFilter初始化！");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html");
        filterChain.doFilter(servletRequest,servletResponse);
        //将request,response向下一个过滤器传递，或者servlet中传递
        //不向别的过滤器或者servlet传递这两个对象，对应的servlet就无法接受请求返回响应了
    }

    @Override
//    销毁：web服务器关闭的时候，过滤器销毁
    public void destroy() {
        System.out.println("CharacterEncodingFilter销毁！");

    }
}
