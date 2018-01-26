<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<%--<%= request.getAttribute("message")%>--%>
<%--<%--%>
<%--long cuurentTime = System.currentTimeMillis();--%>
<%--String timeMessage = "CurrentTime = " + cuurentTime;--%>
<%--%>--%>
<%--<%= timeMessage%>--%>
<form action="${pageContext.request.contextPath}/login" method="post">
    <input type="text" value="login" name="login"/>
    <br/>
    <input type="text" value="password" name="password"/>
    <br/>
    <input type="submit" value="ok"/>
</form>
<p>
    <%=request.getAttribute("access") != null ? request.getAttribute("access") : "" %>
</p>
</body>
</html>
