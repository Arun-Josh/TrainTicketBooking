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
  </style>
</head>
<body>
<%--<% HttpSession ses = request.getSession(true); %>--%>
<form class="box" action="" method="post">
  <h1 >BOOKING SUCCESSFULL <i class="fa fa-check" aria-hidden="true"></i> </h1> 
  <table>
    <table id="t01">
      <tr>
        <th>PNR NUMBER</th>
        <th>TRAIN NO</th>
        <th>TRAIN NAME</th>
        <th>PASSENGER NAME</th>
        <th>DEPARTURE STATION</th>
        <th>ARRIVAL STATION</th>
        <th>SOURCE TIME</th>
        <th>DEST TIME</th>
        <th>SEAT NUMBER</th>
      </tr>
      <tr>
        <td> <%= request.getAttribute("pnr") %> </td>
        <td> <%= request.getAttribute("trainno") %> </td>
        <td> <%= request.getAttribute("trainname") %> </td>
        <td> <%= request.getAttribute("username") %> </td>
        <td> <%= request.getAttribute("source") %> </td>
        <td> <%= request.getAttribute("dest") %> </td>
        <td> <%= request.getAttribute("stime") %> </td>
        <td> <%= request.getAttribute("dtime") %> </td>
        <td> <%= request.getAttribute("seatno") %> </td>
      </tr>
    </table>
    <P>*Please note the PNR number for future reference</P>
</form>

</body>
</html>
