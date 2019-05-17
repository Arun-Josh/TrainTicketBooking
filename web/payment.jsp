<%--
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
<form class="box" action="PaymentGateWay" method="POST">
    <h1>Select Your Mode of Payment</h1>
    <input name="submit" type="submit" value="Credit Card">
    <input name="submit" type="submit" value="Debit Card">
    <input name="submit" type="submit" value="Net Banking">
</form>
</body>
</html>
