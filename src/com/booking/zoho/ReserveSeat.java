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

@WebServlet("/reserveseats")
public class ReserveSeat extends HttpServlet {
    Logger log = Logger.getLogger(ReserveSeat.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        boolean access = new Validations().reserveSeatValidation(request);
        if(!access){
            new Validations().redirectToSearch(response);
            return;
        }
        final MysqlConnectionUtil mysqlDB = new MysqlConnectionUtil();

        try{

                HttpSession session = request.getSession();
                String userid = (String) request.getAttribute("userid");
                String trainid = (String) request.getAttribute("trainid");
                String mailid = (String) request.getAttribute("mailid");
                String modeofpayment = (String) request.getAttribute("modeofpayment");
                String paymentstatus = "SUCCESS";
                String accountnumber = (String) request.getAttribute("accountnumber");
                String ifsccode = (String) request.getAttribute("ifsccode");
                String cardnumber = (String) request.getAttribute("cardnumber");
                String ticketstatus = "CONFIRMED";
                String dateoftravel = (String) request.getAttribute("date");
                String source = (String) request.getAttribute("from");
                String dest = (String) request.getAttribute("to");
                String seattype = (String) request.getAttribute("seattype");
                String fare = (String) request.getAttribute("fare");

                log.info("seeet" + seattype);

                if(userid == null ){
                    out.print("");
                    return;
                }

                log.info(userid + trainid + mailid + modeofpayment + paymentstatus + accountnumber + ifsccode + cardnumber + ticketstatus);

                ResultSet rs = mysqlDB.getStationIDs(source,dest);
                rs.next();
                String sourceid = rs.getString("fromstationid");
                String destid   = rs.getString("tostationid");

                log.info("ffare " +fare);
                mysqlDB.bookTicket(userid,trainid,mailid,modeofpayment,paymentstatus,accountnumber,ifsccode,cardnumber,ticketstatus,dateoftravel,sourceid,destid,fare,seattype);

                rs = mysqlDB.getPnr(mailid,trainid);
                while(rs.next()){}
                rs.previous();
                String pnr = rs.getString("pnr");

                int seats = Integer.valueOf((String)request.getAttribute("seatcount"));
                log.info("seaats : "+ seats);

                System.out.println("passenegers in reserve "+session.getAttribute("passengers"));
                String passenger[] = ((String) session.getAttribute("passengers")).split(",");
                String age[] = ((String) session.getAttribute("ages")).split(",");
                String gender[] = ((String) session.getAttribute("gender")).split(",");

                String seatnumbers = "";
                String srcstopno = (String) request.getAttribute("srcstopno");
                String dststopno = (String) request.getAttribute("dststopno");

                log.info("src dst iinfo " + srcstopno + dststopno );

                ResultSet rsstations = mysqlDB.getTravellingStations(trainid,dststopno,srcstopno);

                while(rsstations.next()){

                    String stationid = rsstations.getString("STATIONID");
                    log.info("st id " + stationid);
                        log.info("seattype "+ seattype);

                        ResultSet rsavail = mysqlDB.getAvailableSeat(trainid, stationid, seattype, dateoftravel);

                        String seatsavailable ="";

                        while(rsavail.next()){
                            seatsavailable = rsavail.getString("seatsavailable");
                            log.info("should come once " + stationid + "   "+seatsavailable);
                        }
                        seatsavailable = String.valueOf(Integer.valueOf(seatsavailable) - seats) ;

                            log.info("seatsavailable after modification "+ seatsavailable + " station " + stationid);

                        mysqlDB.updateAvailableSeats(seatsavailable,trainid,dateoftravel,stationid,seattype);
                }

                int minseatcount =  mysqlDB.getMinSeatCount(trainid,sourceid,destid,seattype,dateoftravel);

                if(minseatcount <=0){
                    mysqlDB.updateStatus(pnr);
                    ticketstatus = "WAITING LIST";
                }

                for(int i=0;i<seats;i++){
                    if(i!=0){
                        seatnumbers+=",";
                    }
                    seatnumbers+= minseatcount ;
                    minseatcount++;
                }

                log.info("seaat nos " +seatnumbers);
                String seatno[] = seatnumbers.split(",");
                log.info("snn" + seatnumbers);
                String status = ticketstatus;
                for(int i=0;i<seats;i++){
                    if(i!=0 && ticketstatus.equals("WAITING LIST")){
                        status+=",WAITING LIST";
                    }
                    else if(i!=0 && ticketstatus.equals("CONFIRMED")){
                        status+=",CONFIRMED";
                    }
                    if(ticketstatus.equals("WAITING LIST")){
                        status+=" "+(Math.abs(minseatcount)+(i+1));
                    }
                    String tstatus ="CONFIRMED";

                    if (ticketstatus.equals("WAITING LIST")){
                        tstatus = "WAITING LIST "+(Math.abs(minseatcount)+(i+1));
                    }

                    mysqlDB.insertPassengers(pnr,passenger[i],Integer.valueOf(seatno[i]),age[i],gender[i],tstatus);
                }
                log.info("passenger status "+status);

                rs = mysqlDB.getTicketTrainInfo(trainid, sourceid, destid);
                rs.next();
                String trainname = rs.getString("trainname");
                String trainnumber = rs.getString("trainnumber");
                String stime = rs.getString("fromsnarrtime");
                String dtime = rs.getString("tosnarrtime");
                JSONObject ticket = new JSONObject();

                ticket.put("pnr",pnr);
                ticket.put("trainid",trainid);
                ticket.put("trainnumber",trainnumber);
                ticket.put("trainname",trainname);
                ticket.put("source",sourceid);
                ticket.put("dest",destid);
                ticket.put("dtime",dtime);
                ticket.put("stime",stime);
                ticket.put("passengers",session.getAttribute("passengers"));
                ticket.put("ages",session.getAttribute("ages"));
                ticket.put("genders",session.getAttribute("gender"));
                ticket.put("ticketstatus",ticketstatus);
                ticket.put("seatnos",seatnumbers);
                ticket.put("seatcount", seats );
                ticket.put("seattype",seattype);
                ticket.put("fare", fare );
                ticket.put("dateoftravel", dateoftravel );
                ticket.put("status",status);

                System.out.println("Passneger s "+ticket.getString("passengers"));
                out.print(ticket);
            }
            catch (Exception E){
                E.printStackTrace();
            }
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
