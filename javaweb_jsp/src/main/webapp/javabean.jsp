<%@ page import="com.tao.pojo.People" %><%--
  Created by IntelliJ IDEA.
  User: tao
  Date: 2021/11/20
  Time: 19:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
//    People people = new People();
//    people.setAddress();
//    people.setAge();
//    people.setId();
//    people.setName();
//    people.getAddress();
//    ......
%>
<%--对象名  类   作用范围    这块基本不用，用上面的java--%>
<jsp:useBean id="people" class="com.tao.pojo.People" scope="page"></jsp:useBean>
<jsp:setProperty name="people" property="address" value="西安"></jsp:setProperty>
<jsp:setProperty name="people" property="age" value="11"></jsp:setProperty>
<jsp:setProperty name="people" property="id" value="1"></jsp:setProperty>
<jsp:setProperty name="people" property="name" value="涛哥"></jsp:setProperty>

id:<jsp:getProperty name="people" property="id"/>
姓名：<jsp:getProperty name="people" property="name"/>
年龄：<jsp:getProperty name="people" property="age"/>
地址：<jsp:getProperty name="people" property="address"/>



</body>
</html>
