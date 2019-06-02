package com.booking.zoho;

import org.json.JSONObject;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.logging.Logger;

@WebServlet("/passengerinfo")
public class PassengerInfo extends HttpServlet {
    Logger LOG = Logger.getLogger(PassengerInfo.class.getName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        int seats = Integer.valueOf(request.getParameter("seatcount"));
        String seattype = request.getParameter("seattype");
        final MysqlConnectionUtil mysqlDB = new MysqlConnectionUtil();

        int srcstopno = Integer.valueOf(request.getParameter("srcstopno"));
        int dststopno = Integer.valueOf(request.getParameter("deststopno"));
        LOG.info("tttid" +  seattype + seats + srcstopno + dststopno);
        String passengers = "";
        String ages = "";
        String gender = "";
        for(int i=1;i<=seats;i++){
            if(i!=1){
                passengers +=",";
                ages +=",";
                gender +=",";
            }
            passengers+= request.getParameter("pass"+i);
            ages += request.getParameter("page"+i);
            gender += request.getParameter("gender"+i);
        }

        HttpSession session = request.getSession();
        session.setAttribute("passengers",passengers);
        session.setAttribute("ages",ages);
        session.setAttribute("gender",gender);
        session.setAttribute("seattype",seattype);

        LOG.info("nnname" + passengers + " ages" + ages + " gneder " + gender);
        LOG.info("tttid" +  request.getParameter("trainid"));

        int stops = Math.abs(srcstopno-dststopno);

        String trainid = request.getParameter("trainid");

        System.out.println("Trainnnn id "+trainid +" s " + srcstopno +" d " + dststopno +" diff "+stops);


        int fare = 1;
        try{
            ResultSet rs = mysqlDB.getFare(seattype,trainid);

            if(rs.next()){
                fare = rs.getInt("fare");
                System.out.println("fare retrieved" +fare);
                fare *= Math.abs(srcstopno-dststopno);
            }else{
                fare = 100 * Math.abs(srcstopno-dststopno);
            }

        }catch (Exception E){
            E.printStackTrace();
        }

        JSONObject fareJSON = new JSONObject();
        try{
            fareJSON.put("fare",fare);
            System.out.println("fare JSON "+fareJSON.getString("fare"));
        }catch (Exception E){
            E.printStackTrace();
        }
        PrintWriter out = response.getWriter();

        out.print(fareJSON);

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
