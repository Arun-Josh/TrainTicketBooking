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
        String modeofpayment = request.getParameter("mode");

        System.out.println("mop" + modeofpayment);
        String cardnumber = "";
        String ifsccode = "";
        String accountnumber = "";
        ServletContext sc = getServletContext();
        sc.setAttribute("modeofpayment",modeofpayment);
        if(modeofpayment.equals("Debit Cars") || modeofpayment.equals("Credit Card")){
//            sc.setAttribute("cardnumber","1234 5678 9098 1234");
            cardnumber = "1234 5678 9098 1234";
        }
        else if(modeofpayment.equals("Net Banking")){
//            sc.setAttribute("ifsccode","SBIN0012345");
//            sc.setAttribute("accountnumber","0123456789213");
            ifsccode = "SBIN0012345";
            accountnumber = "0123456789213";
        }


        String userid =  (String)session.getAttribute("userid");
        String trainid =  request.getParameter("trainid");
        String mailid =  (String) session.getAttribute("mail");
        String date =  request.getParameter("date");
        String from =  request.getParameter("from");
        String to =  request.getParameter("to");
        String seattype =  request.getParameter("seattype");
        String fare =  request.getParameter("fare");
        String seatcount = request.getParameter("seatcount");
//        String passengers =(String)request.getAttribute("passengers");
//        String ages =(String)request.getAttribute("ages");
//        String genders = (String)request.getAttribute("gender");
        String srcstopno =  request.getParameter("srcstopno");
        String deststopno =  request.getParameter("deststopno");



        request.setAttribute("fare",fare);
        request.setAttribute("userid",userid);
        request.setAttribute("trainid",trainid);
        request.setAttribute("mailid",mailid);
        request.setAttribute("modeofpayment",modeofpayment);
        request.setAttribute("accountnumber",accountnumber);
        request.setAttribute("ifsccode",ifsccode);
        request.setAttribute("cardnumber",cardnumber);
        request.setAttribute("date",date);
        request.setAttribute("from",from);
        request.setAttribute("to",to);
        request.setAttribute("seattype",seattype);
        request.setAttribute("seatcount",seatcount);
//        request.setAttribute("passengers",passengers);
//        request.setAttribute("ages",ages);
//        request.setAttribute("genders",genders);
        request.setAttribute("srcstopno",srcstopno);
        request.setAttribute("dststopno",deststopno);
        request.getRequestDispatcher("/reserveseats").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("mail")==null){
            response.sendRedirect("index.html");
        }
        else{
            doPost(request, response);
        }
    }
}
