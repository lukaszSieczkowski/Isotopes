<%@ page import="pl.isotope.bean.Isotope" %>
<%@ page import="pl.isotope.bean.User" %>
<%@ page import="pl.isotope.utils.Activity" %>
<%--
  Date: 2016-09-21
  Time: 20:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User user = (User) session.getAttribute("user"); %>
<% Isotope isotope =(Isotope) session.getAttribute("isotope");%>
<% String alert = (String)request.getAttribute("alert");%>

<html>
<head>
    <title>Isotope Bunker</title>
    <link rel="stylesheet" href="css/welcome_style.css" type="text/css">
</head>
<body>
<div class="container">
    <div class="header1">
        ISOTOPE BUNKER
    </div>

    <div class="header2">
        Welcome: <%=user.getName()%> <%=user.getSurname()%>
    </div>

    <div class="mainBody">

        <div class="left_column">
            <a href="ad_isotope.jsp"><input class="submit" type="submit" value="ADD ISOTOPE"></a>
            <a href="remove_isotope.jsp"><input class="submit" type="submit" value="REMOVE ISOTOPE"></a>
            <a href="select_isotope.jsp"><input class="submit" type="submit" value="UPDATE ISOTOPE"></a>
            <a href="welcome.jsp"><input class="submit" type="submit" value="SHOW ISOTOPES"></a>
            <form action="/LogoutServlet">
                <input class="submit" type="submit" value="LOGOUT">
            </form>
        </div>
        <div class="right_column">
            <form class="add_form" action="/IsotopeServlet?param=update" method="post">
                Update Isotope data :<br>
                <br>
                <input class="input" type="text" name="name" placeholder="Isotope Name" required="true" >
                <input class="input" type="text" name="loadDate" placeholder="Load date (rrrr-mm-dd)" required="true" maxlength="10">
                <input class="input" type="text" name="loadActivity" placeholder="Load activity" required="true" > Ci<br>
                <input class="input" type="radio" name="type" value="1" checked >Selenium
                <input class="input" type="radio" name="type" value="2" >Iridium
                <input class="input" type="radio" name="type" value="3" >Cobalt
                <input class="input" type="radio" name="type" value="4" >Ytterbium<br>
                <input class="submit1" type="submit" value="UPDATE ISOTOPE">
                <div style="color:red;margin-top:15px;">
                    <% if(alert == null){
                        alert = " ";
                    }%>
                    <%=alert%>
                </div>
            </form>
            <table>
                <tr class="table_header">
                    <td>No.</td>
                    <td>Isotope name</td>
                    <td>Isotope type</td>
                    <td>Isotope load date</td>
                    <td>Current activity</td>
                    <td>Days from load date</td>
                </tr>

                <tr>
                    <td>1</td>
                    <td><%=isotope.getIsotopeName()%>
                    </td><td><%=isotope.getIsotopeType()%></td>
                    <td><%=isotope.getIsotopeLoadDate()%></td>
                    <td><%=Activity.calculateActivity(isotope,Activity.calculateDays(isotope.getIsotopeLoadDate()))%> Ci</td>
                    <td><%=Activity.calculateDays(isotope.getIsotopeLoadDate())%></td>
                </tr>

            </table>
        </div>
    </div>
    <div class="fotter">

    </div>
</div>
</body>
</html>