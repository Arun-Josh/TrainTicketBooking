package com.booking.zoho;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
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
            int pnr = 0;
            if(request.getParameter("pnr")!=null){
                pnr = Integer.valueOf(request.getParameter("pnr"));
            }else if(request.getAttribute("pnr")!=null){
                pnr = Integer.valueOf((String) request.getAttribute("pnr"));
            }
            LOG.info("PNR N " +pnr);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM bookings where pnr = ?" );
            ps.setInt(1,pnr);
            ResultSet rs =  ps.executeQuery();
            Boolean status = rs.next();
            if(!status){
                PrintWriter out = response.getWriter();
                out.print("<center><h2 style=\"margin-top: 40px !important\">PNR Number not found !</h2></center>");
                request.getRequestDispatcher("pnrstatus.html").include(request,response);
                return;
            }

            String trainid = rs.getString("trainid");
            String userid = rs.getString("userid");
            String sourceid = rs.getString("source");
            String destid = rs.getString("dest");
            String ticketstatus = rs.getString("TICKETSTATUS");
            String fare = rs.getString("fare");
            String dateoftravel = rs.getString("dateoftravel");

            ps = con.prepareStatement("SELECT  * FROM TRAINNAMES WHERE TRAINID = ?");
            ps.setString(1,trainid);
            rs = ps.executeQuery();
            rs.next();
            String trainname = rs.getString("trainname");
            String trainnumber = rs.getString("trainnumber");

            ps = con.prepareStatement("SELECT  * FROM USERS WHERE USERID = ?");
            ps.setString(1,userid);
            rs = ps.executeQuery();
            rs.next();
            String uname = rs.getString("name");

            ps = con.prepareStatement("SELECT  * FROM STATIONS WHERE TRAINID = ? AND STATIONID = ?");
            ps.setString(1,trainid);
            ps.setString(2,sourceid);
            rs = ps.executeQuery();
            rs.next();
            String stime = rs.getString("stationarrtime");


            ps.setString(2,destid);
            rs = ps.executeQuery();
            rs.next();
            String dtime = rs.getString("stationarrtime");

            ps = con.prepareStatement("SELECT * FROM PASSENGERINFO WHERE PNR = ?");
            ps.setInt(1,pnr);
            rs = ps.executeQuery();
            String passengers = "";
            String ages = "";
            String genders = "";
            String seatnos = "";
            int seatcount = 0;
            while(rs.next()){
                seatcount++;

                passengers += rs.getString("passengername") + ",";
                ages += rs.getString("age") + ",";
                genders += rs.getString("gender")+",";
//                seatnos += rs.getString("seatno");
                seatnos += rs.getString("seatno") + ",";
            }

            LOG.info("SEAT NOOO "+seatnos    +  ticketstatus  );

            ServletContext servletcontext = getServletContext();

            request.setAttribute("pnr",pnr);
            servletcontext.setAttribute("trainno",trainid);
            servletcontext.setAttribute("trainnumber",trainnumber);
            servletcontext.setAttribute("trainname",trainname);
            servletcontext.setAttribute("source",sourceid);
            servletcontext.setAttribute("dest",destid);
            servletcontext.setAttribute("dtime",dtime);
            servletcontext.setAttribute("stime",stime);
            servletcontext.setAttribute("passengers",passengers);
            servletcontext.setAttribute("ages",ages);
            servletcontext.setAttribute("genders",genders);
            request.setAttribute("ticketstatus",ticketstatus);
            request.setAttribute("seatnos",seatnos);
            request.setAttribute("seatcount", seatcount );
            request.setAttribute("fare", fare );
            request.setAttribute("dateoftravel", dateoftravel );
//            LOG.info("ALLL NOO "+seatnos
//                    + trainid
//                    + sourceid
//                    + destid
//                    + dtime
//                    + stime
//                    + passengers
//                    + ages
//                    + genders
//                    +seatnos
//            );
        }
        catch (Exception E){
            E.printStackTrace();
        }
        request.getRequestDispatcher("TicketInfo.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
