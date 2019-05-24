<%@ page import="java.util.ArrayList" %>
<%@ page import="com.booking.zoho.Tickets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Booked Tickets</title>
    <link rel="stylesheet" href="css/ticketpage.css">
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
<%--<% HttpSession ses = request.getSession(true); %>--%>
<form class="box" >

    <%
        ArrayList<Tickets> tickets = (ArrayList<Tickets>) request.getAttribute("tickets");

        if(tickets.size()==0){%>

    <h1> NO TICKETS BOOKED !</h1>

        <%}

        for(int t =0;t<tickets.size();t++) {

            Tickets ticket = tickets.get(t);

            String pnr = ticket.getPnr();
            String ticketstatus = ticket.getTicketstatus();
            String trainname = ticket.getTrainname();
            String trainnumber = ticket.getTrainnumber();
            String source = ticket.getFrom();
            String dest = ticket.getTo();
            String dateoftravel = ticket.getDateoftravel();
            String fare = ticket.getTicketfare();
            String stime = ticket.getStime();
            String dtime = ticket.getDtime();
            String tpnr = pnr;

    %>
    <h1 >TICKET INFO <i class="fa fa-ticket" aria-hidden="true"></i> </h1>
    <h2 >TRAIN INFO  <i class="fa fa-subway" aria-hidden="true"></i>  : </h2>

<div>
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
            <td>  <%= pnr %> </td>
            <TD><%=dateoftravel%></TD>
            <td> <%= trainname %></td>
            <td>  <%= trainnumber %> </td>
            <td> <%= source  %> </td>
            <td> <%= dest  %> </td>
            <td> <%= stime %> </td>
            <td> <%= dtime %> </td>
            <td><%= ticketstatus %>  </td>
            <td> Rs. <%= fare %></td>
        </tr>

    </table>
</div>
    <br>
    <br>

    <h2 >PASSENGERS INFO <i class="fa fa-users" area-hidden="true"></i>  :</h2>

    <table id="t01">
        <tr>
            <th>S.NO</th>
            <th>PASSENGER NAME</th>
            <th>GENDER</th>
            <th>AGE</th>
            <th>SEAT NUMBER</th>
        </tr>

    <%
        int p = 1 ;
        do{
            System.out.println("ttt + "+ t);
        String passengername = ticket.getPassenger();
        String age = ticket.getAge();
        String seatno = ticket.getSeatno();
        String gender = ticket.getGender();
        t++;
        if (  tickets.size() <= t  ){
            break;
        }

        if(Integer.valueOf(seatno) <= 0 ){
            seatno = "NOT ASSIGNED";
        }

        ticket = tickets.get(t);
    %>
        <tr>
            <td> <%= p++ %> </td>
            <td> <%= passengername %> </td>
            <td> <%= gender %> </td>
            <td> <%= age %> </td>
            <td> <%= seatno %> </td>

        </tr>

        <%
//            p++;
            }while(ticket.getPnr().equals(tpnr) );%>
    </table>

    <br><br>
    <hr color="#2ecc71">
    <br><br>
        <%t--;%>
        <%}
        %>


</form>

</body>

</html>
