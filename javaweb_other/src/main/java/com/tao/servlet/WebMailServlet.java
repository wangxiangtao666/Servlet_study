package com.tao.servlet;

import com.tao.pojo.User;
import com.tao.utils.SendMail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebMailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收用户请求，封装成对象
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        User user = new User(username, password, email);
        //用户注册成功后，发送一封邮件
        //使用多线程来发送邮件，让这个servlet请求转发到成功页面。
        SendMail send = new SendMail(user);
        send.start();
        req.setAttribute("msg","注册成功");
        req.getRequestDispatcher("info.jsp").forward(req,resp);

    }
}
