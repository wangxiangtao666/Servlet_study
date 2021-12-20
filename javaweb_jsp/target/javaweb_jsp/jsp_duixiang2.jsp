<%--
  Created by IntelliJ IDEA.
  User: tao
  Date: 2021/11/19
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
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


</body>
</html>
