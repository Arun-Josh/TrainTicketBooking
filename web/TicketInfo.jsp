<%--
  Created by IntelliJ IDEA.
  User: dragon
  Date: 15-05-2019
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Ticket Booked Successfully</title>
  <link rel="stylesheet" href="css/resultpage.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <style>
    h1{
      color: #2ecc71 !important;
    }
    #name{
      float: left;
      color: #2ecc71 !important;
    }
    #pnr{
      float: left;
      color:white;
      text-decoration: none;
    }

    #tsn{
      float: right;
      /*margin-right: 100px ;*/
      color: #2ecc71 !important;
    }
    #ts{
      float: right;
      color:white;
      text-decoration: none;
    }
    p{
      float:right;
      color: #2ecc71 !important;
    }

    table {
      width:100%;
    }
    table, th, td {
      border: 1px solid black;
      border-collapse: collapse;
    }
    th, td {
      padding: 15px;
      text-align: left;
    }
    table#t01 tr:nth-child(even) {
      background-color: #eee;
    }
    table#t01 tr:nth-child(odd) {
      background-color: #fff;
    }
    table#t01 th {
      background-color: black;
      color: white;
    }
    .centergreen{
      color:  #2ecc71 !important;
      text-align: center; !important;
    }
    .centerwhite{
      color: white !important;
      text-align: center; !important;
    }
  </style>
</head>
<body>
<%--<% HttpSession ses = request.getSession(true); %>--%>
<form class="box" action="" method="post">
  <h1 >BOOKING SUCCESSFULL <i class="fa fa-check" aria-hidden="true"></i> </h1> 
<%--  <table>--%>
  <h3 id="name">PNR NUMBER : </h3>
  <h3 id="pnr"> &emsp; <%= request.getAttribute("pnr") %></h3>

  <h3 id="ts"> &emsp; <%= (String) request.getAttribute("ticketstatus") %></h3>
  <h3 id="tsn">TICKET : </h3>

    <table id="t01">
      <tr>
        <th>S.NO</th>
        <th>TRAIN NO</th>
        <th>TRAIN NAME</th>
        <th>PASSENGER NAME</th>
        <th>AGE</th>
        <th>DEPARTURE STATION</th>
        <th>ARRIVAL STATION</th>
        <th>SOURCE TIME</th>
        <th>DEST TIME</th>
        <th>SEAT NUMBER</th>
      </tr>

      <% ServletContext sc = getServletConfig().getServletContext(); %>

      <%
          String passenger[] =  ((String)sc.getAttribute("passengers")).split(",");
          String age[] =    ((String)sc.getAttribute("ages")).split(",");
          String gender[] =    ((String)sc.getAttribute("genders")).split(",");
          String seat[] =  ((String) request.getAttribute("seatnos")).split(",");
          int seatcount = (Integer) request.getAttribute("seatcount");
//        int seatcount = 3;
        for(int i=0;i<seatcount;i++) {%>
      <tr>
        <td> <%= (i+1) %> </td>

        <td> <%= sc.getAttribute("trainnumber") %> </td>
        <td> <%= sc.getAttribute("trainname") %> </td>
        <td> <%= passenger[i] %> </td>
        <td> <%= age[i] %> </td>
        <td> <%= sc.getAttribute("source") %> </td>
        <td> <%= sc.getAttribute("dest") %> </td>
        <td> <%= sc.getAttribute("stime") %> </td>
        <td> <%= sc.getAttribute("dtime") %> </td>
        <td> <%= seat[i] %> </td>
      </tr>

      <%}
      %>
    </table>
  <input type="button" id="cbtn" onclick="printTicket()" value="PRINT TICKET">

  <h3 id="name">AMOUNT PAID : </h3>
  <h3 id="pnr"> &emsp; Rs. <%= (String) request.getAttribute("fare") %></h3>

  <P>*Please note the PNR number for future reference</P>
</form>
<script>
  function printTicket() {
    window.print();
  }
</script>
</body>
</html>
