package com.booking.zoho;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

@WebServlet("/login")
public class Login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String mail = request.getParameter("mail");
        String pass = request.getParameter("pass");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        try {
            //loading drivers for mysql
            Class.forName("com.mysql.jdbc.Driver");

            //creating connection with the database
            Connection con= DriverManager.getConnection
                    ("jdbc:mysql://localhost/trainreservation","root","root");
            Boolean status = false;
            PreparedStatement ps = con.prepareStatement("SELECT * FROM USERS WHERE mailid=? AND password=?");
            ps.setString(1,mail);
            ps.setString(2,pass);
            ResultSet rs = ps.executeQuery();
            status = rs.next();

            if(status){
                String uname = rs.getString("name");
                String userid = rs.getString("userid");
//                String mailid = rs.getString("mailid");

                //Sessions

                session.setAttribute("username",uname);
                session.setAttribute("mail",mail);
                session.setAttribute("userid",userid);
//                ServletContext sc = getServletContext();
//                sc.setAttribute("userid", userid);
//                sc.setAttribute("mailid", mailid);

                if(rs.getInt("privilege")==1){
                    Cookie ck = new Cookie("mail",mail);
                    response.addCookie(ck);
                    RequestDispatcher rd = request.getRequestDispatcher("searchtrain.html");
                    rd.forward(request,response);
                }
                else if(rs.getInt("privilege")==10){
                    out.println("<CENTER>ADMIN DASHBOARD UNDER CONSTRUCTION </CENTER>" );
                }
            }
            else{
                response.getWriter().write("FAILED");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
       Boolean login = new SessionValidation().validate(request,response);
       if(login){
            request.getRequestDispatcher("searchtrain.html").forward(request,response);
       }
       else{
           request.getRequestDispatcher("index.html").forward(request,response);
       }
    }
}
