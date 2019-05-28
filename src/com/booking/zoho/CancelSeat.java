package com.booking.zoho;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

@WebServlet("/cancelseat")
public class CancelSeat extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String passengerid = request.getParameter("passengerid");

        Logger log = Logger.getLogger(CancelSeat.class.getName());
        log.info("IN cancel seats to cancel "+ passengerid);

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");
            PreparedStatement ps = con.prepareStatement("UPDATE PASSENGERINFO SET STATUS = \"CANCELLED\" WHERE PASSENGERID = ?");
            ps.setString(1,passengerid);
            ps.executeUpdate();
            System.out.println("Passenger id "+ passengerid + "IS CANCELLED !");
            response.setContentType("text/plain");
            response.getWriter().write("CANCELLED");
        }
        catch (Exception E){
            E.printStackTrace();
        }
//        request.getRequestDispatcher("bookedtickets").include(request,response);

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
