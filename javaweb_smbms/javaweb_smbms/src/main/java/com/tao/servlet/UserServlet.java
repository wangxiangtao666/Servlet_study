package com.tao.servlet;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.tao.pojo.Role;
import com.tao.pojo.User;
import com.tao.service.role.RoleService;
import com.tao.service.role.RoleServiceImpl;
import com.tao.service.user.UserService;
import com.tao.service.user.UserServiceImpl;
import com.tao.util.Constants;
import com.tao.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if(method!=null&&method.equals("savepwd")){
            this.updatePwd(req,resp);
        }else if(method!=null&&method.equals("pwdmodify")){
            System.out.println("已进入");
            this.pwdModify(req,resp);
        }else if(method!=null&&method.equals("query")){
            this.query(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
//修改旧密码
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从session中获取id
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String newPassword = req.getParameter("newpassword");
        boolean flag=false;
        if(o!=null&& !StringUtils.isNullOrEmpty(newPassword)){
            UserService userService = new UserServiceImpl();
            flag=userService.updatePwd(((User)o).getId(),newPassword);
            if(flag){
                req.setAttribute("message","修改密码成功，请使用新密码登录");
                //密码修改成功，移出session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            }else{
                req.setAttribute("message","密码修改失败");
            }
        }else{
            req.setAttribute("message","新密码有问题");
        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);

    }
//验证旧密码
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp){
        //验证旧密码，session中有用户的密码
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");

        Map<String,String> resultMap = new HashMap<String,String>();
        if(o==null){//session过期了,服务器中没有旧密码了
            resultMap.put("result","sessionerror");
        }else if(StringUtils.isNullOrEmpty(oldpassword)){//输入的密码为空
            resultMap.put("result","error");
        }else{
            String userPassword = ((User)o).getUserPassword();//session中用户的密码
            if(oldpassword.equals(userPassword)){
                resultMap.put("result","true");
            }else{
                resultMap.put("result","false");
            }
        }
        System.out.println(resultMap.get("result"));

        try {
            resp.setContentType("application/json");//返回json类型文件
            PrintWriter writer = resp.getWriter();
            //JSONArray 阿里巴巴的json工具类，转换格式
            //将resultMap=["result","true"]转换成json格式{key:value}

            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();//以前写的关闭了没，刷新了没
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//查询管理用户
    public void query(HttpServletRequest req, HttpServletResponse resp){
        //从前端获取数据
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        //这里初始化防止没有传过来，可能没有选择角色
        int queryUserRole=0;//默认没有选择
        int pageSize = 5;//页面显示记录数
        int currentPageNo = 1;//第一次查，显示第一页
        UserService userService = new UserServiceImpl();

        if(queryUserName==null){
            queryUserName="";
        }
        if(temp!=null&& !temp.equals("")){
            queryUserRole = Integer.parseInt(temp);
        }
        if(pageIndex!=null){
            currentPageNo = Integer.parseInt(pageIndex);
        }
        ///////////////////////////////获取用户总数（分页，上一页，下一页）
        int totalCount =userService.getUserCount(queryUserName,queryUserRole);
        //分页，用工具类（实现很简单）
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);
        int totalPageCount = pageSupport.getTotalPageCount();
        //控制首页和尾页
        if(currentPageNo<1){//如果页数小于1，显示第一页
            currentPageNo = 1;
        }else if(currentPageNo>totalPageCount){//如果页数大于最后一页，显示最后一页
            currentPageNo = totalPageCount;
        }
        //获取用户列表
        List<User> userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        req.setAttribute("userList",userList);//转发的页面的jsp中会获取
        RoleService roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        req.setAttribute("roleList",roleList);
        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);
        req.setAttribute("queryUserName",queryUserName);
        req.setAttribute("queryUserRole",queryUserRole);
        //返回前端，就是转发页面
        try {
            req.getRequestDispatcher("userlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            System.out.println("servlet 没找到");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("io 没找到");
            e.printStackTrace();
        }

    }
}
