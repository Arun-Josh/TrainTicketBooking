package com.booking.zoho;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;



@WebServlet("/login")
public class Login extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Login.class.getName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        String mail = request.getParameter("mail");
        String pass = request.getParameter("pass");
        PrintWriter out = response.getWriter();
        try {
            //loading drivers for mysql
            Class.forName("com.mysql.jdbc.Driver");

            //creating connection with the database
            Connection con= DriverManager.getConnection
                    ("jdbc:mysql://localhost/trainreservation","root","root");
            Boolean status = false;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM USERS WHERE email=? AND pass=?");
            ps.setString(1,mail);
            ps.setString(2,pass);
            ResultSet rs = ps.executeQuery();
            status = rs.next();

            LOGGER.info("this i s a test msg"+ status);
            if(status){
                String uname = rs.getString(2);
                session.setAttribute("username",uname);
                session.setAttribute("mail",mail);
                session.setAttribute("pass",pass);
                RequestDispatcher rd = request.getRequestDispatcher("searchtrain.html");
                rd.include(request,response);
            }
            else{

                RequestDispatcher rd = request.getRequestDispatcher("index.html");
                rd.include(request,response);
                out.append("<html><style>/* Popup container - can be anything you want */\n" +
                        ".popup {\n" +
                        "  position: relative;\n" +
                        "  display: inline-block;\n" +
                        "  cursor: pointer;\n" +
                        "  -webkit-user-select: none;\n" +
                        "  -moz-user-select: none;\n" +
                        "  -ms-user-select: none;\n" +
                        "  user-select: none;\n" +
                        "}\n" +
                        "\n" +
                        "/* The actual popup */\n" +
                        ".popup .popuptext {\n" +
                        "  visibility: visible;\n" +
                        "  width: 160px;\n" +
                        "  background-color: #555;\n" +
                        "  color: #fff;\n" +
                        "  text-align: center;\n" +
                        "  border-radius: 6px;\n" +
                        "  padding: 8px 0;\n" +
                        "  position: absolute;\n" +
                        "  z-index: 1;\n" +
                        "  bottom: 125%;\n" +
                        "  left: 50%;\n" +
                        "  margin-left: -80px;\n" +
                        "}\n" +
                        "\n" +
                        "/* Popup arrow */\n" +
                        ".popup .popuptext::after {\n" +
                        "  content: \"\";\n" +
                        "  position: absolute;\n" +
                        "  top: 100%;\n" +
                        "  left: 50%;\n" +
                        "  margin-left: -5px;\n" +
                        "  border-width: 5px;\n" +
                        "  border-style: solid;\n" +
                        "  border-color: #555 transparent transparent transparent;\n" +
                        "}\n" +
                        "\n" +
                        "/* Toggle this class - hide and show the popup */\n" +
                        ".popup .show {\n" +
                        "  visibility: visible;\n" +
                        "  -webkit-animation: fadeIn 1s;\n" +
                        "  animation: fadeIn 1s;\n" +
                        "}\n" +
                        "\n" +
                        "/* Add animation (fade in the popup) */\n" +
                        "@-webkit-keyframes fadeIn {\n" +
                        "  from {opacity: 0;} \n" +
                        "  to {opacity: 1;}\n" +
                        "}\n" +
                        "\n" +
                        "@keyframes fadeIn {\n" +
                        "  from {opacity: 0;}\n" +
                        "  to {opacity:1 ;}\n" +

                        "}" +
                        "#prompt{" +
                        "margin-top :90px; !important" +
                        "</style><body><center><div id=\"prompt\" class=\"popup\">\n" +
                        "  <span class=\"popuptext\" id=\"myPopup\">Username or Password incorrect !</span>\n" +
                        "</div><center></body></html>");
            }
//            out.print(status+" "+rs.getString(1)+" " +rs.getString(2)+rs.getString(3)+" " +rs.getString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
