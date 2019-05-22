package com.booking.zoho;

import javax.servlet.RequestDispatcher;
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
import java.util.logging.Logger;

@WebServlet("/PaymentGateWay")
public class Payment extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String modeofpayment = request.getParameter("submit");

        System.out.println("mop" + modeofpayment);

        ServletContext sc = getServletContext();
        sc.setAttribute("modeofpayment",modeofpayment);
        if(modeofpayment.equals("Debit Cars") || modeofpayment.equals("Credit Card")){
            sc.setAttribute("cardnumber","1234 5678 9098 1234");
        }
        else if(modeofpayment.equals("Net Banking")){
            sc.setAttribute("ifsccode","SBIN0012345");
            sc.setAttribute("accountnumber","0123456789213");
        }
        sc.setAttribute("fare",request.getParameter("fare"));

        request.getRequestDispatcher("/reserveseats").forward(request,response);

//        String[] train = ((String)session.getAttribute("traininfo")).split("#");
//        String mail = (String) session.getAttribute("mail");
//        String trainname = train[0];
//        String source = train[1];
//        String dest = train[2];
//        String stime = train[3];
//        String dtime = train[4];
//        String trainid = train[5];
//
//        String pnr = String.valueOf(session.getAttribute("pnr"));
//        String passengers = String.valueOf(session.getAttribute("passengers"));
//        String seatno = String.valueOf(session.getAttribute("seatno"));
//
//        String paymentmode = request.getParameter("submit");
//
//        Logger log = Logger.getLogger(Payment.class.getName());
//        log.info("payments " + paymentmode);
//
//        try{
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");
//            PreparedStatement ps = con.prepareStatement("UPDATE BOOKINGS SET payment = ? where pnr = ?");
//            ps.setString(1,paymentmode);
//            ps.setString(2,pnr);
//            ps.executeUpdate();
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
//            request.setAttribute("paymentmode",paymentmode);
//
//            request.getRequestDispatcher("TicketInfo.jsp").forward(request,response);
//        }
//        catch (Exception E){
//
//        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
