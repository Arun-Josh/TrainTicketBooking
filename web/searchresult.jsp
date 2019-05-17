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

<form class="box" action="booking" method="POST">
    <h1>Search Results</h1>
    <% ArrayList<Train> data = (ArrayList<Train>)request.getAttribute("trains");

    for(Train train : data){%>
    <input id="rad" name="traininfo" type="radio" value= "<%= train.getTrainname()+"#"+train.getSource()+"#"+train.getDest()+"#"+train.getSourcetime()+"#"+train.getDesttime()+"#"+train.getTrainnumber() %>" required > <a id="ch"> <%= " <a id=\"side\"> Train Name : </a> "+
      "<a id=\"op\">" + train.getTrainname() +"</a> " + "<a id=\"side\"> Source :</a>"+ "<a id=\"op\"> "+train.getSource()+"</a>" +" <a id=\"side\"> Destination : </a>" +
        "<a id=\"op\">" + train.getDest() + "</a> " + "<a id=\"side\"> Departure Time : "+ "<a id=\"op\"> "+ train.getSourcetime() + "</a>" +" <a id=\"side\"> Arrival Time : </a>" + "<a id=\"op\">"+train.getDesttime()+"</a>"
        +"</br></br>" +
    "<a id=\"seat\">Seats Available : </a>" + "<a id=\"side\">"+ train.getRemseats() + "</a>"
%> </a>



    <hr>
    <%}%>

    <input type="submit" value="Book">
</form>
<script>

</script>
<%--        <h1> com.booking.zoho.Train <%= request.getAttribute("trains") %> </h1>--%>
</body>
</html>
