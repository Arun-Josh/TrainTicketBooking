package com.booking.zoho;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.print.Book;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;

@WebServlet(name = "booking")
public class Booking extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = Logger.getLogger(Booking.class.getName());
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String traininfo = request.getParameter("traininfo");

//        String trainname = train[0];
//        String source = train[1];
//        String dest = train[2];
//        String stime = train[3];
//        String dtime = train[4];
//        String trainid = train[5];
//        int seatno = 0;

        HttpSession session = request.getSession(true);
        session.setAttribute("traininfo",traininfo);
        String uname = (String)session.getAttribute("username");

        if (uname==null) {
            out.print("<body><script>alert(\"Please Login Again !\")</script></body>");
            RequestDispatcher rd = request.getRequestDispatcher("index.html");
            rd.include(request,response);
        }
        String mail  = (String) session.getAttribute("mail");
        session.setAttribute("mail",mail);
//        Logger log = Logger.getLogger(Booking.class.getName());
//        log.info("book i "+ uname+" STIME "+stime+"  dtime "+dtime);
//        for(String s:train)
//            log.info(s +" ");
        try{
            request.getRequestDispatcher("PassengerInfo.jsp").forward(request,response);
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
