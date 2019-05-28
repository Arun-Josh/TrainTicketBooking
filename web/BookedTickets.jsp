<%@ page import="java.util.ArrayList" %>
<%@ page import="com.booking.zoho.Tickets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Booked Tickets</title>

    <link rel="stylesheet" href="css/ticketpage.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
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
        button{
            background-color: #f44336;
            color: white;
            padding: 14px 25px;
            text-align: center;

        }
        button:hover{
            background-color: red;
        }
    </style>

</head>
<body>

<form id="tickform" action="cancelseat" class="box" method="post" >

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
<%--            <th>TICKET STATUS</th>--%>
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
<%--            <td><%= ticketstatus %>  </td>--%>
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
            <th>STATUS</th>
            <th>CANCELLATION</th>
        </tr>

    <%
        int p = 1 ;
        do{

            System.out.println("ttt + "+ t + " ts size "+tickets.size() );
            ticket = tickets.get(t);
            if(!ticket.getPnr().equals(tpnr)){
                break;
            }
            String passengername = ticket.getPassenger();
            String passid = ticket.getPassengerid();
            String age = ticket.getAge();
            String seatno = ticket.getSeatno();
            String gender = ticket.getGender();
            String status = ticket.getTicketstatus();
            String cancelbtn = "CANCEL THIS TICKET";
            if(status.equals("CANCELLED")){
                cancelbtn = "CANCELLED SUCCESSFULLY";
            }
            t++;
            if(Integer.valueOf(seatno) <= 0 ){
                seatno = "NOT ASSIGNED";
            }

    %>
        <tr>
            <td> <%= p++ %> </td>
            <td> <%= passengername %> </td>
            <td> <%= gender %> </td>
            <td> <%= age %> </td>
            <td> <%= seatno %> </td>
            <td id="<%=passid%>" > <%= status %> </td>
            <td>
                <input id="passid" name="passengerid" type="hidden" value="">
                <button id="canbtn" name="<%=passid%>" onclick="refresh(this)" type="button" <% if(status.equals("CANCELLED")) {%> <%= "disabled" %>  <%}  %> value="<%=passid%>"><%=cancelbtn%></button>
            </td>
        </tr>

        <%
                if (  tickets.size() <= t ){
                    break;
                 }
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
<script
        src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
<script>

    function ajaxCall(passid) {
        console.log("INSIDE AJAX CALL "+passid);
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if(xhr.status == 200 && xhr.readyState==4){
                // alert(xhr.responseText);
                if(xhr.responseText=="CANCELLED"){
                    document.getElementById(passid).innerHTML = "CANCELLED";
                    document.getElementsByName(passid)[0].innerHTML = "CANCELLED SUCCESSFULLY";
                    document.getElementsByName(passid)[0].disabled = true;
                }
            }
        }
        xhr.open("POST","cancelseat",true);
        xhr.setRequestHeader("content-type","application/x-www-form-urlencoded");
        xhr.send("passengerid="+passid);
    }

    function refresh(el) {
        var passid = document.getElementById("passid").value = el.value;
        console.log("CLICK MADE TO CANCEL " + passid);
        // var form = document.getElementById("tickform");


        $.confirm({
            theme:'supervan',
            useBootstrap : false,
            closeIcon: true,
            icon: 'glyphicon glyphicon-heart',
            columnClass: 'small',
            title: 'Do you sure want to cancel the Ticket ?',
            content: '',
            autoClose: 'NO|10000',
            buttons: {
                YES: {
                    text: 'YES',
                    action: function () {
                        ajaxCall(passid);
                        $.alert('Ticket Successfully Cancelled');
                    }
                },
                NO:{
                    text:'NO',
                    action : function () {
                        $.alert('Ticket Not Cancelled');
                }
                }
            }
        });

        // form.submit();


    }
</script>

</body>

</html>
