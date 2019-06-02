package com.booking.zoho;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.logging.Logger;

@WebServlet("/Registration")
public class Registration extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        final MysqlConnectionUtil mysqlDB = new MysqlConnectionUtil();

        String uname = request.getParameter("user");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String pass  = request.getParameter("pass1");
        String phno  = request.getParameter("phno");

        try{

            ResultSet rscheck = mysqlDB.getUserByMailID(email);

            if(rscheck.next()){
                out.print("Mail ID already registered");
                return;
            }

            int n = mysqlDB.insertUsers(email,phno,uname,gender,pass);
            Logger log = Logger.getLogger(Registration.class.getName());
            log.info("ps returns "+n);
                out.print("SUCCESS");
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            doPost(request, response);

    }
}
