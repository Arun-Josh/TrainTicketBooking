package com.booking.zoho;

import javax.servlet.ServletContext;
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
import java.sql.ResultSet;
import java.util.logging.Logger;

@WebServlet("/passengerinfo")
public class PassengerInfo extends HttpServlet {
    Logger LOG = Logger.getLogger(PassengerInfo.class.getName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        int seats = Integer.valueOf(request.getParameter("seatcount"));
        String seattype = request.getParameter("seattype");

        String passengers = "";
        String ages = "";
        String gender = "";
        for(int i=1;i<=seats;i++){
            if(i!=1){
                passengers +=",";
                ages +=",";
                gender +=",";
            }
            passengers+= request.getParameter("pname"+i);
            ages += request.getParameter("page"+i);
            gender += request.getParameter("gender"+i);
        }
        ServletContext sc = getServletContext();
        sc.setAttribute("passengers",passengers);
        sc.setAttribute("ages",ages);
        sc.setAttribute("genders",gender);
        sc.setAttribute("seatcount", seats);
        sc.setAttribute("seattype",seattype);

        LOG.info("nnname" + passengers + " ages" + ages + " gneder " + gender);
        LOG.info("tttid" +  sc.getAttribute("trainid"));

        int srcstopno =  Integer.valueOf(sc.getAttribute("srcstopno").toString());
        int dststopno =  Integer.valueOf(sc.getAttribute("dststopno").toString());

        int stops = Math.abs(srcstopno-dststopno);

        String trainid = (String) sc.getAttribute("trainid");

        System.out.println("Trainnnn id "+trainid +" s " + srcstopno +" d " + dststopno +" diff "+stops);

        request.setAttribute("seattype",seattype);
        request.setAttribute("trainid",trainid);
        request.setAttribute("seatcount",seats);
        request.setAttribute("stops",stops);

        try{
            request.getRequestDispatcher("payment.jsp").forward(request,response);
        }
        catch (Exception E){
            E.printStackTrace();
        }

//        try {
//            String seatno = "";
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");
//            PreparedStatement ps = con.prepareStatement("SELECT * FROM TRAINS WHERE trainname= ? ");
//            ps.setString(1,trainname);
//            ResultSet rs = ps.executeQuery();
//            rs.next();
//
//            for(int i=0;i<seats;i++){
//                if(i!=0)
//                    seatno+=",";
//                    seatno += String.valueOf( rs.getInt(8) - rs.getInt(9) + 1 +i);
//            }
//
//            int remseats = rs.getInt(9)-seats;
//
//            if (remseats<1){
//                //SEAT FULL
//                //No further Operation
//            }
//
//
//            ps = con.prepareStatement("UPDATE TRAINS SET remseats = ? WHERE TRAINNAME = ?");
//            ps.setInt(1,remseats);
//            ps.setString(2,trainname);
//            ps.executeUpdate();
//
//            ps = con.prepareStatement("Insert INTO Bookings(trainname,passengers,source,dest,sourcetime,desttime,seatno,mail,trainid, payment) VALUES(?,?,?,?,?,?,?,?,?,?)");
//            ps.setString(1,trainname);
//            ps.setString(2,passengers);
//            ps.setString(3,source);
//            ps.setString(4,dest);
//            ps.setString(5,stime);
//            ps.setString(6,dtime);
//            ps.setString(7,seatno);
//            ps.setString(8,mail);
//            ps.setString(9,trainid);
//            ps.setString(10,"Credit / Debit Card");
//            ps.executeUpdate();
//
//            ps = con.prepareStatement("SELECT * FROM BOOKINGS WHERE trainname= ? AND mail=? ");
//            ps.setString(1,trainname);
//            ps.setString(2,mail);
//            rs = ps.executeQuery();
//
//            while (rs.next()){}
//            int pnr= 0;
//
//            if(rs.previous()){
//                pnr = rs.getInt(1);
//            }
//
//            session.setAttribute("pnr",pnr);
//            session.setAttribute("passengers",passengers);
//            session.setAttribute("seatno",seatno);
//
//            request.setAttribute("pnr",pnr);
//            request.setAttribute("trainno",trainid);
//            request.setAttribute("trainname",trainname);
//            request.setAttribute("username",passengers);
//            request.setAttribute("source",source);
//            request.setAttribute("dest",dest);
//            request.setAttribute("dtime",dtime);
//            request.setAttribute("stime",stime);
//            request.setAttribute("seatno",seatno);
//
//            request.getRequestDispatcher("payment.jsp").forward(request,response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

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
