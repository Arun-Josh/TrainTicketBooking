<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: dragon
  Date: 17-05-2019
  Time: 11:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Payment GateWay</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div>
    <form action="bookedtickets" method="post">
        <input type="submit" id="bhistory" class="submit-button" value="BOOKED TICKETS">
    </form>
</div>

<%
    System.out.println("paymment " + request.getAttribute("trainid") +" "+ request.getAttribute("seatcount") + " " +request.getAttribute("seattype"));
    String trainid = (String) request.getAttribute("trainid");
    String seatcount = String.valueOf( request.getAttribute("seatcount"));
    String seattype = (String) request.getAttribute("seattype");
    int stops = Integer.valueOf(request.getAttribute("stops").toString());

    Class.forName("com.mysql.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");
    PreparedStatement ps = con.prepareStatement("SELECT * FROM FARES WHERE TRAINID = ? AND SEATTYPE = ?");
    ps.setString(1,trainid);
    ps.setString(2,seattype);
    ResultSet rs = ps.executeQuery();
    int fare= 0;
    if(rs.next()){
        fare = rs.getInt("FARE") * stops;
    }
    int total = fare * Integer.valueOf(seatcount);

%>

<form class="box" action="PaymentGateWay" method="POST">
    <h1>TICKET FARE </h1>

    <h3 id="green"> TOTAL AMOUNT</h3> <h2 id="white"> <%=  fare+ " X " + seatcount + " = "  + total%> </h2>

    <h4 id="green">SELECT YOUR MODE OF PAYMENT</h4>
<%--    <h1><%=trainid + " "+ seatcount + " "+ seattype%> </h1>--%>

    <input name="submit" type="submit" value="Credit Card">
    <input name="submit" type="submit" value="Debit Card">
    <input name="submit" type="submit" value="Net Banking">
    <input name="fare" type="hidden" value="<%=total%>">

</form>
</body>
</html>
