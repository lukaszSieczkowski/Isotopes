<%--

  Date: 2016-09-25
  Time: 12:14
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String alert = (String) request.getAttribute("alert");%>
<html>
<head>
    <meta charset="utf-8">
    <title>Index</title>
    <link rel="stylesheet" type="text/css" href="css/index_style.css">
</head>
<body>
<div class="container">

    <div class="left">
    </div>

    <div class="right">
        <div class="right_top">
        </div>
        <div class="right_middle1">
            <form action="LoginServlet?param=login" method="post">
                <input class="input" type="email" name="email" placeholder="Email" required="true">
                <input class="input" type="password" name="password" placeholder="Password" required="true">
                <input class="submit" type="submit" value="LOGIN">
                <div class="alert">
                    <% if (alert == null) {
                        alert = " ";
                    }%>
                    <%=alert%>
                </div>
            </form>
        </div>
        <div class="right_middle2">
            <div class="right_middle2_left">
                <a href="forgotten_password.jsp">I forgot my password</a>
            </div>
            <div class="right_middle2_right">
                <a href="first_login.jsp">First login</a>
            </div>
        </div>
        <div class="right_bottom">

        </div>
    </div>

</div>
</body>
</html>
