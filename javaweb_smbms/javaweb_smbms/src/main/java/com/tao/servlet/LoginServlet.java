package com.tao.servlet;

import com.tao.pojo.User;
import com.tao.service.user.UserService;
import com.tao.service.user.UserServiceImpl;
import com.tao.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取用户名密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        //调用业务层，和数据库中的密码进行对比
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode,userPassword);

        if(user!=null){//有此人，密码正确
            //将用户信息放到session中
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            //跳转到主页
            resp.sendRedirect("jsp/frame.jsp");
        }else{//无此人，无法登录
            //转发回登录页面，用request带提示信息
            req.setAttribute("error","用户名或密码错误");
            req.getRequestDispatcher("login.jsp").forward(req,resp);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
