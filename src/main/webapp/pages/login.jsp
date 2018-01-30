<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<form name="f" action="/login" method="post">
    <input type="text" value="vik" name="username"/>
    <input type="text" value="pass" name="userpass"/>
    <input type="submit" value="submit"/>
</form>
<p>
    <%=request.getParameter("error")!=null ? request.getParameter("error"):"" %>
</p>
</body>
</html>
