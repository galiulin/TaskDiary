<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DashBoard</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<c:forEach items="${requestScope.tasks}" var="task">
    <c:out value="${task.description}"></c:out>
    <c:out value="${task.dateAdd}"></c:out>
    <c:out value="${task.condition}"></c:out>
    <a href="/inner/task?taskId=${task.id}"><button>VIEW</button></a>
    <br>
</c:forEach>
</body>
</html>
