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
  <link rel="stylesheet" href="css/pinfopage.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  
</head>
<body>
<% request.getAttribute("trainname");%>
<form class="box" action="passengerinfo" method="post">
    <input id= "seatcount" name= "seatcount" type="number" placeholder="Number of Passengers">
    <input id= "cbtn" type="button" onclick="userinfo()" value="Confirm">
  <h1 >Enter Passenger Details</h1> 
  <table>
    <table id="t01">
      <tr>
          <th>Name</th>
          <th>Age</th>
          <th>Gender</th>
      </tr>

      <tbody id="newrow"></tbody>
               
    </table>

    <div id="sbutton">

    </div>

    <P>*Please Enter Details As per your goverment issued ID</P>
    
</form>
<script>
  function userinfo() {
    var seat = document.getElementById("seatcount").value;
    var i = 1;
    while(seat>0){
      document.getElementById("newrow").innerHTML +=  ' <tr> '
                +  '<td><input type="text" name= "pname'+ i +'" placeholder="Passenger '+ i +' Name" required>  </td>' 
                +  '<td> <input type="number" name= "page'+ i +'" placeholder="Passenger '+ i +' Age" required> </td>' 
                +  '<td> <input name="gender'+ i +'" type="radio" Value="Male" required><a id="ch">MALE</a>'  
                +   '<input name="gender'+ i +'" type="radio" Value="Female" required><a id="ch">FEMALE</a>'
                +    '<input name="gender'+ i +'" type="radio" Value="Others" required> <a id="ch">OTHERS</a> </td> ' 
                + '</tr>';
      i++;
      // document.getElementById("newrow").innerHTML +=  "HAI !";
      seat--;
    }
      document.getElementById("sbutton").innerHTML += '<input type="submit" value = "Confirm Booking" >';
      document.getElementById("cbtn").style.display = "none";
      document.getElementById("seatcount").style.display = "none";

  }
</script>
</body>
</html>

