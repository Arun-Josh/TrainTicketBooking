package com.booking.zoho;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

public class Validations {
    protected final boolean cancelSeat(String passengerid, String userid){
        final MysqlConnectionUtil mysqlDB = new MysqlConnectionUtil();
        try{

            ResultSet rs = mysqlDB.validateTicketAuthority(passengerid,userid);
            if(rs.next()){
                return true;
            }
        }catch (Exception E){
            E.printStackTrace();
        }
        return false;
    }

    protected final boolean passengerPageValidation(HttpServletRequest request){
        // To Prevent Direct Access
        return request.getParameter("seatcount") != null && request.getParameter("seattype") != null && request.getParameter("srcstopno") != null && request.getParameter("deststopno") != null;
    }

    protected final boolean paymentPageValidation(HttpServletRequest request){
        String date =  request.getParameter("date");
        String from =  request.getParameter("from");
        String to =  request.getParameter("to");
        String seattype =  request.getParameter("seattype");
        String fare =  request.getParameter("fare");
        String seatcount = request.getParameter("seatcount");
        String srcstopno =  request.getParameter("srcstopno");
        String deststopno =  request.getParameter("deststopno");

        return date != null && from != null && to != null && seattype != null && fare != null && seatcount != null && srcstopno != null && deststopno != null;
    }
    protected final boolean registerPageValidation(HttpServletRequest request){
        String uname = request.getParameter("user");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String pass  = request.getParameter("pass1");
        String phno  = request.getParameter("phno");

        return uname != null && email != null && gender != null && pass != null && phno != null;
    }
    protected final boolean reserveSeatValidation(HttpServletRequest request){
        String userid = (String) request.getAttribute("userid");
        String trainid = (String) request.getAttribute("trainid");
        String mailid = (String) request.getAttribute("mailid");
        String modeofpayment = (String) request.getAttribute("modeofpayment");
        String accountnumber = (String) request.getAttribute("accountnumber");
        String ifsccode = (String) request.getAttribute("ifsccode");
        String cardnumber = (String) request.getAttribute("cardnumber");
        String dateoftravel = (String) request.getAttribute("date");
        String source = (String) request.getAttribute("from");
        String dest = (String) request.getAttribute("to");
        String seattype = (String) request.getAttribute("seattype");
        String fare = (String) request.getAttribute("fare");

        return userid != null && trainid != null && mailid != null && modeofpayment != null && accountnumber != null &&
                ifsccode != null && cardnumber != null && dateoftravel != null && source != null && seattype != null && dest != null && fare != null;
    }

    protected final void accessDeniedPrompt(HttpServletResponse response){
        try{
            response.getWriter().print("<center>ACCESS DENIED !</center>");
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }

    protected final boolean searchValidation(HttpServletRequest request){
        String source = request.getParameter("from");
        String dest   = request.getParameter("to");
        String date = request.getParameter("date");
        return source != null && dest != null && date != null;
    }

    protected final void redirectToSearch(HttpServletResponse response){
        try {
            response.getWriter().print("<script>window.location.href = 'searchtrain.html'</script>");
        }catch (Exception E){
            E.printStackTrace();
        }
    }

    protected final void redirectToRegister(HttpServletResponse response){
        try{
            response.getWriter().print("<script>window.location.href = 'register.html'</script>");
        }catch (Exception E){
            E.printStackTrace();
        }
    }

    protected final boolean pnrCheckValidation(HttpServletRequest request){
        String pnr = request.getParameter("pnr");
        return pnr != null;
    }

    protected final void redirectToPnrSearch(HttpServletResponse response){
        try{
            response.getWriter().print("<script>window.location.href='pnrstatus.html'</script>");
        }catch (Exception E){
            E.printStackTrace();
        }
    }

}
