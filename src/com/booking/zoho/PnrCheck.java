package com.booking.zoho;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.logging.Logger;

@WebServlet("/pnrcheck")
public class PnrCheck extends HttpServlet {
    Logger LOG = Logger.getLogger(PnrCheck.class.getName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        boolean access = new Validations().pnrCheckValidation(request);

        if(!access){
            new Validations().redirectToPnrSearch(response);
            return;
        }

        final MysqlConnectionUtil mysqlDB = new MysqlConnectionUtil();
        try {
            LOG.info(request.getParameter("pnr"));
            int pnr = 0;
            if(request.getParameter("pnr")!=null){
                pnr = Integer.valueOf(request.getParameter("pnr"));
            }else if(request.getAttribute("pnr")!=null){
                pnr = Integer.valueOf((String) request.getAttribute("pnr"));
            }
            LOG.info("PNR N " +pnr);

            ResultSet rs = mysqlDB.getTicketByPnr(pnr);
            Boolean status = rs.next();
            if(!status){
                PrintWriter out = response.getWriter();
                out.print("INVALID");
                System.out.println("PNR NOT FOUND !");
                return;
            }

            String trainid = rs.getString("trainid");
            String userid = rs.getString("userid");
            String sourceid = rs.getString("source");
            String destid = rs.getString("dest");
            String fare = rs.getString("fare");
            String dateoftravel = rs.getString("dateoftravel");
            String seattype = rs.getString("seattype");
            String trainname = rs.getString("trainname");
            String trainnumber = rs.getString("trainnumber");
            String stime = rs.getString("fromstationarrtime");
            String dtime = rs.getString("tostationarrtime");

            rs = mysqlDB.getPassengerInfo(pnr);
            String passengers = "";
            String ages = "";
            String genders = "";
            String seatnos = "";
            int seatcount = 0;

            String ticketstatus = "";
            while(rs.next()){
                seatcount++;
                ticketstatus+=rs.getString("status")+",";
                passengers += rs.getString("passengername") + ",";
                ages += rs.getString("age") + ",";
                genders += rs.getString("gender")+",";
                seatnos += rs.getString("seatno") + ",";
            }

            LOG.info("SEAT NOOO "+seatnos    +  ticketstatus  );

            JSONObject ticket = new JSONObject();

            ticket.put("pnr",pnr);
            ticket.put("trainid",trainid);
            ticket.put("trainnumber",trainnumber);
            ticket.put("trainname",trainname);
            ticket.put("source",sourceid);
            ticket.put("dest",destid);
            ticket.put("dtime",dtime);
            ticket.put("stime",stime);
            ticket.put("passengers",passengers);
            ticket.put("ages",ages);
            ticket.put("genders",genders);
            ticket.put("ticketstatus",ticketstatus);
            ticket.put("seatnos",seatnos);
            ticket.put("seatcount", seatcount );
            ticket.put("seattype",seattype);
            ticket.put("fare", fare );
            ticket.put("dateoftravel", dateoftravel );
            ticket.put("status",ticketstatus);

            System.out.println("In pnr check JSON Passneger s "+ticket.getString("passengers"));
            response.getWriter().print(ticket);

        }
        catch (Exception E){
            E.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request, response);
    }
}
