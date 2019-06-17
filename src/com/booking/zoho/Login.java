package com.booking.zoho;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

//@WebServlet("/login")
public class Login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String mail = request.getParameter("mail");
        String pass = request.getParameter("pass");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        try {
            System.out.println("checking mail pwd "+mail +" "+ pass);

            ResultSet rs = new MysqlConnectionUtil().authLogin(mail,pass);
            Boolean status = rs.next();

            if(status){
                String uname = rs.getString("name");
                String userid = rs.getString("userid");

                //Sessions
                session.setAttribute("username",uname);
                session.setAttribute("mail",mail);
                session.setAttribute("userid",userid);

                if(rs.getInt("privilege")==1){
                    Cookie ck = new Cookie("mail",mail);
                    response.addCookie(ck);
                    out.print("OK");
                }
                else if(rs.getInt("privilege")==10){
                    out.print("admin");
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
      doPost(request,response);
    }
}
