<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>

<form action="${pageContext.request.contextPath}/login" method="post">
    <input type="text" value="login" name="login"/>
    <br/>
    <input type="text" value="password" name="password"/>
    <br/>
    <input type="submit" value="ok"/>
</form>

<p>
    <font color="red">
        <%=request.getParameter("access") != null ? request.getParameter("access") : "" %>
    </font>
</p>
</body>
</html>
