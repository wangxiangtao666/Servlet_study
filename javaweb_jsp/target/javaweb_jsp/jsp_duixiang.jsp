<%--
  Created by IntelliJ IDEA.
  User: tao
  Date: 2021/11/19
  Time: 20:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--内置对象--%>
<%
    pageContext.setAttribute("name1","涛1");//保存的数据只在一个页面中有效,
              // 这一个jsp文件中可以获取，访问一个jsp,就是访问它转换成的java的一个函数_jspService().返回response中信息到客户端,所以这个保存数据保存时是在服务器中。
    request.setAttribute("name2","涛2");//保存的数据只在一次请求中有效，请求转发会将request作为参数传递，也是有效的，数据保存在request中（request在服务器中）
    session.setAttribute("name3","涛3");//保存的数据只在一次会话中有效，从session创建，到浏览器关闭（或者session过期）
              // （session就是为每个浏览器创建的房间，钥匙（索引号）通过cookie给浏览器），数据保存在房间中（还是在服务器）。
    application.setAttribute("name4","涛4");//保存的数据在服务器中有效，从打开服务器到关闭服务器。(应该只在一个项目中，服务器可以管理多个项目)
              //数据也是在服务器中

//    PageContext类中定义的常量
//    public static final int PAGE_SCOPE = 1;
//    public static final int REQUEST_SCOPE = 2;
//    public static final int SESSION_SCOPE = 3;
//    public static final int APPLICATION_SCOPE = 4;
//  ===========================================================

//    pageContext.setAttribute("name1","涛1",PageContext.PAGE_SCOPE);//通过第三个参数可以让这一种形式实现上述四种形式，不常用
//    pageContext.setAttribute("name2","涛1",PageContext.REQUEST_SCOPE);
//    pageContext.setAttribute("name3","涛1",PageContext.SESSION_SCOPE);
//    pageContext.setAttribute("name4","涛1",PageContext.APPLICATION_SCOPE);

%>

<%  //从pageContext取出，通过寻找的方式
    //从作用范围小->作用范围大：pageContext->request->session->application
    String name1 = (String) pageContext.findAttribute("name1");
    String name2 = (String) pageContext.findAttribute("name2");
    String name3 = (String) pageContext.findAttribute("name3");
    String name4 = (String) pageContext.findAttribute("name4");
    String name5 = (String) pageContext.findAttribute("name5");//不存在

%>
<%--    使用EL表达式输出，${}这个表达式相当于<%=%>--%>
<h1>取出的值为：</h1>
<h3>${name1}</h3>
<h3>${name2}</h3>
<h3>${name3}</h3>
<h3>${name4}</h3>
<h3>${name5}</h3><%--这个啥也不输出，这是EL表达式和一般jsp表达式的区别--%>
<hr>
<h3><%=name5%></h3><%--这将输出null--%>


<%
    pageContext.forward("/index.jsp");
    request.getRequestDispatcher("/index.jsp").forward(request,response);
%>




</body>
</html>
