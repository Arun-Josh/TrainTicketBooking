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

      h2{
          color: #2ecc71;
          text-transform: uppercase;
          font-weight: 500;
          text-align: left !important;
      }

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

<form class="box">

  <% ServletContext sc = getServletConfig().getServletContext(); %>

  <h1 >TICKET INFO <i class="fa fa-ticket" aria-hidden="true"></i> </h1>
    <h2 >TRAIN INFO : <i class="fa fa-subway" aria-hidden="true"></i> </h2>
  <table id="t01">
    <tr>
      <th>PNR NUMBER</th>
      <th>DATE OF TRAVEL</th>
      <th>TRAIN NAME</th>
      <th>TRAIN NUMBER</th>
      <th>SOURCE</th>
      <th>DESTINATION</th>
      <th>SOURCE TIME</th>
      <th>DESTINATION TIME</th>
      <th>TICKET STATUS</th>
      <th>TICKET FARE</th>
    </tr>

    <tr>
      <td>  <%= request.getAttribute("pnr") %> </td>
      <td>  <%= request.getAttribute("dateoftravel") %> </td>
      <td> <%= sc.getAttribute("trainname") %></td>
      <td>  <%= sc.getAttribute("trainnumber") %> </td>
      <td> <%= sc.getAttribute("source")  %> </td>
      <td> <%= sc.getAttribute("dest")  %> </td>
      <td> <%= sc.getAttribute("stime") %> </td>
      <td> <%= sc.getAttribute("dtime") %> </td>
      <td><%= (String) request.getAttribute("ticketstatus") %>  </td>
      <td> Rs. <%= (String) request.getAttribute("fare") %></td>
    </tr>

  </table>

  <h2 >PASSENGERS INFO : <i class="fa fa-users" area-hidden="true"></i> </h2>

  <table id="t01">
      <tr>
        <th>S.NO</th>
        <th>PASSENGER NAME</th>
        <th>AGE</th>
        <th>SEAT NUMBER</th>
        <th>GENDER</th>
      </tr>

      <%
          String passenger[] =  ((String)sc.getAttribute("passengers")).split(",");
          String age[] =    ((String)sc.getAttribute("ages")).split(",");
          String gender[] =    ((String)sc.getAttribute("genders")).split(",");
          String seat[] =  ((String) request.getAttribute("seatnos")).split(",");
          int seatcount = (Integer) request.getAttribute("seatcount");
        for(int i=0;i<seatcount;i++) {

          if(Integer.valueOf(seat[i]) <= 0 ){
            seat[i] = "NOT ASSIGNED";
          }

      %>
      <tr>
        <td> <%= (i+1) %> </td>
        <td> <%= passenger[i] %> </td>
        <td> <%= age[i] %> </td>
        <td> <%= seat[i] %> </td>
        <td> <%= gender[i] %></td>
      </tr>

      <%}
      %>
    </table>
  <input type="button" id="cbtn" onclick="printTicket()" value="PRINT TICKET">

  <P>*Please note the PNR number for future reference</P>
</form>
<script>
  function printTicket() {
    window.print();
  }
</script>
</body>
</html>
