package com.booking.zoho;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet("/bookedtickets")
public class BookedTickets extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Boolean login = new SessionValidation().validate(request,response);
        MysqlConnectionUtil mysqlDB = new MysqlConnectionUtil();
        if(!login){
            response.sendRedirect("index.html");
            return;
        }
        HttpSession session = request.getSession();

        String userid = (String) session.getAttribute("userid");
        System.out.println("booking userid" + userid);

        try {

            ResultSet rs = mysqlDB.getBookedTickets(userid);

            ArrayList<Tickets> tickets = new ArrayList<>();

            while (rs.next()){
                System.out.println("rs + " + rs);

                String pnr = rs.getString("pnr");
                String from = rs.getString("source");
                String to = rs.getString("dest");
//                String trainid = rs.getString("trainid");
                String seattype = rs.getString("seattype");
                String trainname = rs.getString("trainname");
                String trainnumber = rs.getString("trainnumber");
                String stime = rs.getString("fromstationarrtime");
                String  dtime = rs.getString("tostationarrtime");
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
                tickets.add(new Tickets(passengerid, pnr,from,to,trainnumber,trainname,ticketstatus,dateoftravel,ticketfare,passengername, age, seatno, gender, stime, dtime, seattype));
            }

            String ticketsJSON = new Gson().toJson(tickets);
            System.out.println("Tickets Json "+ticketsJSON);
            response.getWriter().print(ticketsJSON);
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Boolean login = new SessionValidation().validate(request,response);
        if(login){
            doPost(request,response);
        }
        else{
            response.sendRedirect("index.html");
        }
    }
}
