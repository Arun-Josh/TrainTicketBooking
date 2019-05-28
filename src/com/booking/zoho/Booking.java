package com.booking.zoho;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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

        HttpSession session = request.getSession();

//        if(session.getAttribute("mailid")==null){
//            response.sendRedirect("index.jsp");
//        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String[] traininfo = request.getParameter("traininfo").split("#");
        logger.info("ttinfoo "+request.getParameter("traininfo"));
        String trainid = traininfo[0];
        String srcstopno = traininfo[7];
        String dststopno = traininfo[8];

//        HttpSession session = request.getSession(true);
        session.setAttribute("traininfo",traininfo);
        String uname = (String)session.getAttribute("username");

        if (uname==null) {
            out.print("<body><script>alert(\"Please Login Again !\")</script></body>");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.include(request,response);
        }
//        String mail  = (String) session.getAttribute("mail");
//        session.setAttribute("mail",mail);
//        request.setAttribute("trainid",trainid);


        ServletContext sc = getServletContext();
        sc.setAttribute("trainid", trainid);
        sc.setAttribute("trainname", traininfo[1]);
        sc.setAttribute("from",traininfo[2]);
        sc.setAttribute("to",traininfo[3]);
        sc.setAttribute("srcstopno",srcstopno);
        sc.setAttribute("dststopno",dststopno);

        try{
            request.getRequestDispatcher("PassengerInfo.jsp").forward(request,response);
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("mail")==null){
            response.sendRedirect("index.jsp");
        }
        else{
            doPost(request, response);
        }
    }
}
