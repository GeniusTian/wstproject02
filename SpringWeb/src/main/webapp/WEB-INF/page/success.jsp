<%--
  Created by IntelliJ IDEA.
  User: 王适天
  Date: 2019/6/29
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>success</title>
</head>
<body>
<h1>成功页面</h1>
<h1>${requestScope.map}</h1><br/>
<h2>${sessionScope.model}</h2><br/>
<h3>${applicationScope.modelMap}</h3>
</body>
</html>
