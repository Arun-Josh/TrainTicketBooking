<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %><%--
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
  <link rel="stylesheet" href="css/pinfopage.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  
</head>
<body>

<div>
    <form action="logout" method="post">
        <input type="submit" id="blogout" class="submit-button" value="LOG OUT">
    </form>
</div>

<%  ServletContext sc = getServletConfig().getServletContext();%>

<form class="box" action="passengerinfo" method="post">

    <a id="from"> FROM :</a> <a id="fromname"> <%= sc.getAttribute("from")%> </a>
    <a id="trainname"> TRAIN : </a> <a id="ch"> <%= sc.getAttribute("trainname")%> </a>
    <a id="toname"> <%= sc.getAttribute("to")%> </a> <a id="to"> TO :</a>
    <br><br>
    <input id= "seatcount" name= "seatcount" type="number" placeholder="Number of Passengers"  pattern="[1-5]{1}" >
    <input id= "cbtn" type="button" onclick="userinfo()" value="Confirm">

    <%String trainid = (String )sc.getAttribute("trainid");%>
    <%
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");
        PreparedStatement ps= con.prepareStatement("SELECT * FROM SEATSINFO WHERE TRAINID = ?");
        ps.setString(1,trainid);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) { %>
               <input name = "seattype" type="radio" value="<%= rs.getString("seattype") %>" required>  <a id="ch"> <%= rs.getString("seattype") %> </a>
      <%}%>
    <h1 >Enter Passenger Details</h1>
    <table id="t01">
      <tr>
          <th>Name</th>
          <th>Age</th>
          <th>Gender</th>
      </tr>

      <tbody id="newrow"></tbody>
               
    </table>

    <div id="sbutton"> </div>

    <P id="pright">*Please Enter Details As per your goverment issued ID</P>
    <P id="pleft">Only upto four seats can be booked per ticket</P>
    
</form>
<script>
  function userinfo() {
    var seat = document.getElementById("seatcount").value;
    var i = 1;
    var t = seat;
    while(seat>0 && seat <=4){
      document.getElementById("newrow").innerHTML +=  ' <tr> '
                +  '<td><input id="dark" type="text" name= "pname'+ i +'" placeholder="Passenger '+ i +' Name" required>  </td>'
                +  '<td> <input type="text" name= "page'+ i +'" placeholder="Passenger '+ i +' Age" pattern="[0-9]{1,3}" required> </td>'
                +  '<td> <input name="gender'+ i +'" type="radio" Value="Male" required><a id="dark">MALE</a>'
                +   '<input name="gender'+ i +'" type="radio" Value="Female" required><a id="dark">FEMALE</a>'
                +    '<input name="gender'+ i +'" type="radio" Value="Others" required> <a id="dark">OTHERS</a> </td> '
                + '</tr>';
      i++;
      // document.getElementById("newrow").innerHTML +=  "HAI !";
      seat--;
    }
    if(t > 0 && t <=4){
        document.getElementById("sbutton").innerHTML += '<input type="submit" value = "Confirm Booking" >';
        document.getElementById("cbtn").style.display = "none";
        document.getElementById("seatcount").style.display = "none";

    }

  }
</script>
</body>
</html>

