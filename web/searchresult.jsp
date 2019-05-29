<%@ page import="java.util.ArrayList" %>
<%@ page import="com.booking.zoho.Train" %>
<%--
  Created by IntelliJ IDEA.
  User: dragon
  Date: 15-05-2019
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Trains Available</title>
    <link rel="stylesheet" href="css/resultpage.css">
</head>
<body>
    <%
        if(session.getAttribute("mail")==null){
            response.sendRedirect("index.html");
        }

    %>
        <div>
            <form action="searchtrain.html" method="post">
                <input type="submit" id="home" class="submit-button" value="HOME">
            </form>
        </div>

<%--        <div>--%>
<%--            <form action="bookedtickets" method="post">--%>
<%--                <input type="submit" id="bhistory" class="submit-button" value="BOOKED TICKETS">--%>
<%--            </form>--%>
<%--        </div>--%>

        <div>
            <form action="logout" method="post">
                <input type="submit" id="blogout" class="submit-button" value="LOG OUT">
            </form>
        </div>



<form class="box" action="booking" method="POST">
    <h1>Search Results</h1>
    <hr>
    <% ArrayList<Train> data = (ArrayList<Train>)request.getAttribute("trains");

    if(data.size()==0){ %>
        <%= "<h3 id=\"op\"> NO TRAINS FOUND !</h3>" %>
        <script>        window.onload = function (ev) { document.getElementById("book").style.display = "none"; }  </script>
    <%}
    else
    for(Train train : data){%>
    <input id="rad" name="traininfo" type="radio" value= "<%= train.getTrainid()+"#"+train.getTrainname()+"#"+train.getSource()+"#"+train.getDest()+"#"+train.getSourcetime()+"#"+train.getDesttime()+"#"+train.getTrainnumber()+"#"+train.getSrcstop()+"#"+train.getDststop() %>" required > <a id="ch">
    <%= "<a id=\"side\"> Train Number : </a> "+
      "<a id=\"op\">" + train.getTrainnumber() +"</a> " +
    " <a id=\"side\"> Train Name : </a> "+
      "<a id=\"op\">" + train.getTrainname() +"</a> " +

      "<a id=\"side\"> Source :</a>"+ "<a id=\"op\"> "+train.getSource()+"</a>" +" <a id=\"side\"> Destination : </a>" +
        "<a id=\"op\">" + train.getDest() + "</a> " + "<a id=\"side\"> Departure Time : "+ "<a id=\"op\"> "+ train.getSourcetime() + "</a>" +" <a id=\"side\"> Arrival Time : </a>" + "<a id=\"op\">"+train.getDesttime()+"</a>"
        +"</br></br>" +

        "<a id=\"seat\">Seats Available  &emsp; </a><br><br>"
        %>
        <% for(int i=0;i<train.getSeats().size();i++) {
        String seatsavailable = String.valueOf(train.getSeats().get(i).getSeatcount());
        if(Integer.valueOf(seatsavailable)<0){
            seatsavailable = "WL "+(Math.abs(Integer.valueOf(seatsavailable)) + 1);
        }
        %>
            <%= " <a id=\"OP\"> "+ train.getSeats().get(i).getSeattype() + ":"+"</a>" + "  " + "<a id=\"side\">"+ seatsavailable + "</a>&emsp;" %>
<%--</a>--%>
        <%}%>
        <br><br>

    <hr>
    <%}%>

    <input id="book" type="submit" value="Book">
</form>
<script>

    var a = document.cookie;
    if(a==""){
        window.location.href="index.html";
    }
</script>
<%--        <h1> com.booking.zoho.Train <%= request.getAttribute("trains") %> </h1>--%>
</body>
</html>
