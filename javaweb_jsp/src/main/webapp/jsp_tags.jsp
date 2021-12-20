<%@ page import="java.net.URLEncoder" %><%--
  Created by IntelliJ IDEA.
  User: tao
  Date: 2021/11/20
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>111111111111</h1>
<%--主要就是这 inclue forward param标签--%>

<%--这是个页面 前面需要加web路径/--%>
<jsp:include page="/common/header.jsp"></jsp:include>
<h1>这是主体</h1>
<jsp:include page="/common/footer.jsp"></jsp:include>

<%--下面的注释不能放到标签中，会报500,贼坑--%>
<%--jsp:forward 相当于pageContext.forword("")--%>
<%--jsp:param 相当于向请求转发中的ruquest添加信息--%>
<%
    request.setCharacterEncoding("utf-8");
%>
<jsp:forward page="/jsp_tag2.jsp">
    <jsp:param name="name" value="涛哥"></jsp:param>
    <jsp:param name="age" value="12"></jsp:param>
</jsp:forward>



</body>
</html>
