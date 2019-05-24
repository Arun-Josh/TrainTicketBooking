package com.booking.zoho;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/cancelseat")
public class CancelSeat extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String passengerid = request.getParameter("passengerid");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");
            PreparedStatement ps = con.prepareStatement("UPDATE PASSENGERINFO SET STATUS = \"CANCELLED\" WHERE PASSENGERID = ?");
            ps.setString(1,passengerid);
            ps.executeUpdate();
            System.out.println("Passenger id "+ passengerid + "IS CANCELLED !");
        }
        catch (Exception E){
            E.printStackTrace();
        }
        request.getRequestDispatcher("bookedtickets").include(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
