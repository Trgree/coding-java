<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="login" method="post">
    <strong>${message}</strong>
    <p>
        <label>username:<input name="username"></label>
    </p>
    <p>
        <label>password:<input name="password"></label>
    </p>
    <p>
        <button type="submit" >登录</button>
    </p>
</form>
</body>
</html>

