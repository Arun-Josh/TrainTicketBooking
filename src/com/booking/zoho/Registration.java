package com.booking.zoho;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/Registration")
public class Registration extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
//        out.print("Reg servlet");
        String uname = request.getParameter("user");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String pass  = request.getParameter("pass1");
        String phno  = request.getParameter("phno");
//        out.print(uname+email+gender+pass);
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");
            PreparedStatement ps = con.prepareStatement("Insert INTO USERS(email,phno,name,gender,pass) VALUES(?,?,?,?,?)");
            ps.setString(1,email);
            ps.setString(2,phno);
            ps.setString(3,uname);
            ps.setString(4,gender);
            ps.setString(5,pass);
            ps.executeUpdate();
            out.print("<body><script>alert(\"Account Creation Success\")</script></body>");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.include(request,response);
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
