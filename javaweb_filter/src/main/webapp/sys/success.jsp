<%--
  Created by IntelliJ IDEA.
  User: tao
  Date: 2021/11/21
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--如果不添加过滤器，只能本页自己判断是否用户可以进入--%>
<%
//    String username = (String) request.getSession().getAttribute("username");
//    if(username==null){//只判断session中是否有用户名
//        response.sendRedirect("/filter/error.jsp");
//    }

%>


<h1>主页</h1>
<a href="/filter/logout">撤销登录</a>返回登录页
</body>
</html>
