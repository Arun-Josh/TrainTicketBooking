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
        if(request.getParameter("seatcount")==null || request.getParameter("seattype")==null || request.getParameter("srcstopno")==null || request.getParameter("deststopno")==null){
            return false;
        }
        else {
            return true;
        }
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

        if(date==null || from == null || to == null || seattype ==null || fare == null || seatcount == null || srcstopno == null || deststopno == null){
            return false;
        }
        else {
            return true;
        }
    }
    protected final boolean registerPageValidation(HttpServletRequest request){
        String modeofpayment = request.getParameter("mode");
        String trainid =  request.getParameter("trainid");
        String date =  request.getParameter("date");
        String from =  request.getParameter("from");
        String to =  request.getParameter("to");
        String seattype =  request.getParameter("seattype");
        String fare =  request.getParameter("fare");
        String seatcount = request.getParameter("seatcount");
        String srcstopno =  request.getParameter("srcstopno");
        String deststopno =  request.getParameter("deststopno");

        if (modeofpayment==null || trainid==null || date==null || from==null || to ==null || seattype==null || fare==null || seatcount==null || srcstopno ==null || deststopno==null){
            return false;
        }
        else {
            return true;
        }
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

        if(userid==null || trainid == null || mailid ==null || modeofpayment == null || accountnumber == null ||
                ifsccode==null || cardnumber==null || dateoftravel==null || source==null || seattype == null || dest==null || fare ==null){
            return false;
        }
        else {
            return true;
        }
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
        if(source==null || dest == null || date ==null){
            return false;
        }
        else {
            return true;
        }
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
        if(pnr==null){
            return false;
        }
        else return true;
    }

    protected final void redirectToPnrSearch(HttpServletResponse response){
        try{
            response.getWriter().print("<script>window.location.href='pnrstatus.html'</script>");
        }catch (Exception E){
            E.printStackTrace();
        }
    }

}
