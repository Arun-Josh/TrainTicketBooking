package com.booking.zoho;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

@WebServlet("/pnrcheck")
public class PnrCheck extends HttpServlet {
    Logger LOG = Logger.getLogger(PnrCheck.class.getName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");
            LOG.info(request.getParameter("pnr"));
            int pnr = Integer.valueOf(request.getParameter("pnr"));
            LOG.info("PNR N " +pnr);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM bookings where pnr = ?" );
            ps.setInt(1,pnr);
            ResultSet rs =  ps.executeQuery();
            rs.next();

            String trainid = rs.getString(2);
            String trainname = rs.getString(3);
            String uname = rs.getString(4);
            String source = rs.getString(5);
            String dest = rs.getString(6);
            String stime = rs.getString(7);
            String dtime = rs.getString(8);
            String seatno = rs.getString("seatno");
            LOG.info("SEAT NOO "+seatno);

            request.setAttribute("pnr",pnr);
            request.setAttribute("trainno",trainid);
            request.setAttribute("trainname",trainname);
            request.setAttribute("username",uname);
            request.setAttribute("source",source);
            request.setAttribute("dest",dest);
            request.setAttribute("dtime",dtime);
            request.setAttribute("stime",stime);
            request.setAttribute("seatno",seatno);

        }
        catch (Exception E){
            E.printStackTrace();
        }
        request.getRequestDispatcher("TicketInfo.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
