<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task</title>
</head>
<body>
<form action="/task" method="post">
    <h1>Страница просмотра и редактирования задачи</h1>
    <h3>id задачи</h3>
    <input type="text" value="${requestScope.task.id}" name="taskId"/>
    <h3>заголовок</h3>
    <h1><input type="text" value="${requestScope.task.title}" name="title"/></h1>
    <h2>описание</h2>
    <h3><input type="text" value="${requestScope.task.description}" name="description"/></h3>
    <h3>состояние</h3>
    <h3><input type="text" value="${requestScope.task.condition}" name="condition"/></h3>
    <h3>id пользователя</h3>
    <h3><input type="text" value="${requestScope.task.userId}" name="personInCharge"/></h3>
    <h3>дата добавления</h3>
    <h3><input type="text" value="${requestScope.task.dateAdd}" name="dateAdd"/></h3>
    <h3>дата завершения</h3>
    <h3><input type="text" value="${requestScope.task.deadLine}" name="deadLine"/></h3>
    <br/>
    <input type="submit" value="send"/>
    <a href="/dashboard">
        <button>Go to main page</button>
    </a>

</form>


</body>
</html>
