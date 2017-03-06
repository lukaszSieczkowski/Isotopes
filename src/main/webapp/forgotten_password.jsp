<%--
  Date: 2016-09-28
  Time: 14:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String alert = (String) request.getAttribute("alert");%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/first_login_style.css">
    <title>Forgotten Password</title>
</head>
<body>
<div class="container">
    <div class="upper_margin_forgotten_password">

    </div>
    <div class="registration_forgotten_pass">
        <form action="LoginServlet?param=remind" method="post">
            <input class="input" type="email" name="email" placeholder="Email" required="true">
            <input class="submit" type="submit" value="SEND PASSWORD">
            <div class="alert"></div>
        </form>
        <div class="alert">

            <% if (alert == null) {
                alert = " ";
            }%>
            <%=alert%>

        </div>
    </div>
</div>
</body>
</html>
