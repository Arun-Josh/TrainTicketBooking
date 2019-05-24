package com.booking.zoho;

import javax.servlet.ServletContext;
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
import java.util.ArrayList;

@WebServlet("/bookedtickets")
public class BookedTickets extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext sc = getServletContext();
        String userid = (String) sc.getAttribute("userid");
        System.out.println("buserid" + userid);

        try {
//            Class.forName("java.mysql.jdbc.Driver");
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation", "root", "root");
            PreparedStatement ps = con.prepareStatement("SELECT BOOKINGS.PNR, BOOKINGS.USERID, BOOKINGS.TRAINID,\n" +
                    "                    BOOKINGS.TICKETSTATUS, BOOKINGS.DATEOFTRAVEL, BOOKINGS.SOURCE, BOOKINGS.DEST, BOOKINGS.FARE, \n" +
                    "                    PASSENGERINFO.PASSENGERID, PASSENGERINFO.PASSENGERNAME, PASSENGERINFO.SEATNO, PASSENGERINFO.AGE, PASSENGERINFO.GENDER, PASSENGERINFO.STATUS\n" +
                    "                    FROM BOOKINGS,PASSENGERINFO  WHERE BOOKINGS.USERID = ? AND PASSENGERINFO.PNR = BOOKINGS.PNR;");
            ps.setString(1, userid);
            ResultSet rs = ps.executeQuery();

            ArrayList<Tickets> tickets = new ArrayList<>();

            while (rs.next()){
                System.out.println("rs + " + rs);

                String pnr = rs.getString("pnr");
                String from = rs.getString("source");
                String to = rs.getString("dest");
                String trainid = rs.getString("trainid");

                PreparedStatement pstrain = con.prepareStatement("SELECT * FROM TRAINNAMES WHERE TRAINID = ?");
                pstrain.setString(1,trainid);
                ResultSet rstrain = pstrain.executeQuery();
                String trainname = "";
                String trainnumber = "";

                if(rstrain.next()){
                    trainname = rstrain.getString("trainname");
                    trainnumber = rstrain.getString("trainnumber");
                }

                PreparedStatement psstations = con.prepareStatement("SELECT * FROM STATIONS WHERE TRAINID = ? AND STATIONID = ?");
                psstations.setString(1,trainid);
                psstations.setString(2,from);
                ResultSet rsstations = psstations.executeQuery();

                String stime = "";
                if (rsstations.next()){
                    stime = rsstations.getString("stationarrtime");
                }

                psstations.setString(2,to);
                rsstations = psstations.executeQuery();

                String dtime = "";
                if (rsstations.next()){
                    dtime = rsstations.getString("stationarrtime");
                }

                String ticketstatus = rs.getString("status");
                String dateoftravel = rs.getString("dateoftravel");
                String ticketfare = rs.getString("fare");
                String passengername = rs.getString("passengername");
                String seatno = rs.getString("seatno");
                String age = rs.getString("age");
                String gender = rs.getString("gender");
                String passengerid = rs.getString("passengerid");
                System.out.println(passengerid +" "+pnr + " + " +from + " + " +to + " + " +trainnumber + " + " +trainname + " + " +ticketstatus + " + " +dateoftravel + " + " +ticketfare + " + " +passengername + " + " + age + " + " + seatno + " + " + gender + " + " +stime+ " + "+ dtime);
                System.out.println("passss id +"+passengerid);
//    public Tickets(String pnr, String from, String to, String trainnumber, String trainname, String ticketstatus, String dateoftravel, String ticketfare, String passenger, String age, String seatno, String gender) {
                tickets.add(new Tickets(passengerid, pnr,from,to,trainnumber,trainname,ticketstatus,dateoftravel,ticketfare,passengername, age, seatno, gender, stime, dtime));
            }

                request.setAttribute("tickets",tickets);
                request.getRequestDispatcher("BookedTickets.jsp").include(request,response);

        }
        catch (Exception E){
            E.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
