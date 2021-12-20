<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: tao
  Date: 2021/11/20
  Time: 13:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--引入jstl核心标签库，我们才能使用jstl核心标签--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<>
<h4>if测试</h4>

<form action="jsp_jstl_core.jsp" methond="get">

    <input type="text" name="username" >
    <input type="submit" value="登录">
</form>

<%--判断如果提交的用户名是管理员，则登录成功--%>
<%--test中写判断条件 var 指定一个变量接收boolean结果--%>
<%--out标签中value写要输出的值,用request可以获取参数，但${}获取不到request--%>
<%--    EL表达式获取表单中的数据
        ${param.参数名}
--%>
<c:if test="${param.username=='admin'}" var = "isAdmin">
    <c:out value="管理员欢迎您"></c:out>
</c:if>
<c:out value="isAdmin"></c:out>

<%--定义一个变量score,值为85--%>
<c:set var="score" value="85"></c:set>
<c:choose>
    <c:when test="${score>=90}">
        你的成绩为优秀
    </c:when>
    <c:when test="${score>=80}">
        你的成绩为一般
    </c:when>
    <c:when test="${score>=70}">
        你的成绩为良好
    </c:when>
    <c:when test="${score>=60}">
        你的成绩为不及格
    </c:when>

</c:choose>

<%
    ArrayList<String> people = new ArrayList<>();
    people.add(0,"张三");//可以指定索引
    people.add(1,"李四");
    people.add(2,"王五");
    people.add(3,"赵六");
    people.add(4,"田七");
    request.setAttribute("list",people);

%>
<%--var 指定接收每一次接收遍历出来的变量
items 指定要遍历的对象--%>
<c:forEach var="people" items="${list}">
    <c:out value="${people}"></c:out><br>
</c:forEach>
<hr>
<c:forEach var="people" items="${list}" begin="1" end="3" step="2">
    <c:out value="${people}"></c:out><br>
</c:forEach>




</body>
</html>
