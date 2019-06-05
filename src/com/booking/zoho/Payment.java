package com.booking.zoho;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/PaymentGateWay")
public class Payment extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        boolean access =  new Validations().paymentPageValidation(request);

        if (!access){
            response.getWriter().print("<center> ACCESS DENIED !</center>");
            return;
        }

        String modeofpayment = request.getParameter("mode");

        System.out.println("mop" + modeofpayment);
        String cardnumber = "";
        String ifsccode = "";
        String accountnumber = "";

        String userid =  (String)session.getAttribute("userid");
        String trainid =  request.getParameter("trainid");
        String mailid =  (String) session.getAttribute("mail");
        String date =  request.getParameter("date");
        String from =  request.getParameter("from");
        String to =  request.getParameter("to");
        String seattype =  request.getParameter("seattype");
        String fare =  request.getParameter("fare");
        String seatcount = request.getParameter("seatcount");
        String srcstopno =  request.getParameter("srcstopno");
        String deststopno =  request.getParameter("deststopno");

        if(modeofpayment.equals("Debit Card") || modeofpayment.equals("Credit Card")){
            cardnumber = "1234 5678 9098 1234";
        }
        else if(modeofpayment.equals("Net Banking")){
            ifsccode = "SBIN0012345";
            accountnumber = "0123456789213";
        }


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
