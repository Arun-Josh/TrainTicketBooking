<!DOCTYPE html>
<head>
    <title> Train Ticket Reservation </title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<%
if (session!=null){
    if( session.getAttribute("mail")!=null){
        System.out.println("http session params +" + session.getAttribute("mail"));
        response.sendRedirect("searchtrain.html");
    }
}
%>
    <div>
            <form id="login" class="box" action="login" method="POST">
                    <h1>Login</h1>
                    <input name="mail" type="email" placeholder="Email ID" required>
                    <input name="pass"  type="password" placeholder="Password" required>
                    <input type="submit" value="Login">
                    <a href="register.html">Create new Account</a>
                </form>
    </div>

<div>
        <button id="bpnr" class="submit-button">PNR STATUS </button>
</div>
    
    
    <script type="text/javascript">

        function showalert(){
            alert("Username or Password Incorrect !!");
        }

    document.getElementById("bpnr").onclick = function (){
        location.href = "pnrstatus.html";
    };

    </script>
</body>