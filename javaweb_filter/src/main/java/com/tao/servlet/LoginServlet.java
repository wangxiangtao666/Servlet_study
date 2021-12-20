package com.tao.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        System.out.println(username);
        if(username.equals("admin")){
            //添加session
            req.getSession().setAttribute("username",username);
            //登录成功后重定向
            resp.sendRedirect("/filter/sys/success.jsp");//重定向不会带请求信息，所以之前要放入session中
        }else{
            resp.sendRedirect("/filter/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
