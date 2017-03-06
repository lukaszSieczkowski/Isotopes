<%--
  Date: 2016-09-25
  Time: 17:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String alert = (String) request.getAttribute("alert");%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/first_login_style.css">
    <title>First login</title>
</head>
<body>
<div class="container">
    <div class="upper_margin">
    </div>

    <div class="registration">
        <form action="LoginServlet?param=create" method="post">
            <input class="input" type="text" name="name" placeholder="Name" required="true">
            <input class="input" type="text" name="surname" placeholder="Surname" required="true">
            <input class="input" type="email" name="email" placeholder="Email" required="true">
            <input class="input" type="password" name="password" placeholder="Password" required="true">
            <input class="input" type="password" name="re_password" placeholder="Repeat Password" required="true">
            <input class="submit" type="submit" value="REGISTER">
            <div class="alert">

                <% if (alert == null) {
                    alert = " ";
                }%>
                <%=alert%>

            </div>
        </form>
    </div>
</div>
</body>
</html>
