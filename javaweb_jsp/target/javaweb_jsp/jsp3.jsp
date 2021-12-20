<%--
  Created by IntelliJ IDEA.
  User: tao
  Date: 2021/11/19
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--这个用的是文件路径--%>
<%@ include file="common/header.jsp"%>
<h1>这是主体</h1>
<%@ include file="common/footer.jsp"%>


<%--jsp标签--%>
<%--这是个页面 前面需要加web路径/ --%>
<jsp:include page="/common/header.jsp"></jsp:include>
<h1>这是主体</h1>
<jsp:include page="/common/footer.jsp"></jsp:include>

</body>
</html>
