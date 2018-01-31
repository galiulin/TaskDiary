<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Create an account</title>
    <meta charset="utf-8">
</head>

<body>

<div class="container">
    <form method="POST" action="${pageContext.request.contextPath}/registration"
          class="form-signin">

        <h2>Create your account</h2>

        <div class="form-group ${status.error ? 'has-error' : ''}">
            <br/>
            <input name="login" type="text" class="form-control"
                   placeholder="Login"
                   autofocus="true"/>
            <br/>

            <input name="firstName" type="text" class="form-control"
                   placeholder="First Name"/>

            <br/>
            <input name="lastName" type="text" class="form-control"
                   placeholder="Last Name"/>

            <br/>
            <input name="password" type="password" class="form-control"
                   placeholder="Password"/>
            <br/>
            <input name="confirmPassword" type="password" class="form-control"
                   placeholder="Confirm your password"/>
            <br/>

            <button class="btn btn-lg btn-primary btn-block"
                    type="submit">Submit
            </button>
        </div>
    </form>

</div>
</body>
</html>