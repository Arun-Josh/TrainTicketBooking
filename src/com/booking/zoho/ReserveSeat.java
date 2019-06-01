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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

@WebServlet("/reserveseats")
public class ReserveSeat extends HttpServlet {
    Logger log = Logger.getLogger(ReserveSeat.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
        PrintWriter out = response.getWriter();
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

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");

                ResultSet rs = new MysqlConnectionUtil().getStation(source);
                rs.next();
                String sourceid = rs.getString("stationid");

                rs = new MysqlConnectionUtil().getStation(dest);
                rs.next();
                String destid   = rs.getString("stationid");
                log.info("ffare " +fare);
                new MysqlConnectionUtil().bookTicket(userid,trainid,mailid,modeofpayment,paymentstatus,accountnumber,ifsccode,cardnumber,ticketstatus,dateoftravel,sourceid,destid,fare,seattype);

                rs = new MysqlConnectionUtil().getPnr(mailid,trainid);
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

                PreparedStatement psstations = con.prepareStatement("SELECT  * FROM STATIONS WHERE TRAINID = ? AND STOPNO >= ? AND STOPNO < ? ");
                psstations.setString(1,trainid);
                psstations.setString(2,dststopno);
                psstations.setString(3,srcstopno);
                ResultSet rsstations = psstations.executeQuery();

                while(rsstations.next()){

                    String stationid = rsstations.getString("STATIONID");
                    log.info("st id " + stationid);
                        log.info("seattype "+ seattype);

                        ResultSet rsavail = new MysqlConnectionUtil().getAvailableSeats(trainid, stationid, seattype, dateoftravel);

                        String seatsavailable ="";

                        while(rsavail.next()){
                            seatsavailable = rsavail.getString("seatsavailable");
                            log.info("should come once " + stationid + "   "+seatsavailable);
                        }
                        seatsavailable = String.valueOf(Integer.valueOf(seatsavailable) - seats) ;

                            log.info("seatsavailable after modification "+ seatsavailable + " station " + stationid);

                    PreparedStatement psseatsavail = con.prepareStatement("    UPDATE SEATSAVAILABLE SET SEATSAVAILABLE = ? WHERE TRAINID = ? AND DAY = ? AND STATIONID = ?  AND SEATTYPE = ?");
                        psseatsavail.setString(1,seatsavailable);
                        psseatsavail.setString(2,trainid);
                        psseatsavail.setString(3,dateoftravel);
                        psseatsavail.setString(4,stationid);
                        psseatsavail.setString(5,seattype);
                        psseatsavail.executeUpdate();
                }

                PreparedStatement psseatavail = con.prepareStatement("SELECT  * FROM SEATSAVAILABLE WHERE TRAINID = ? AND STATIONID = ? AND SEATTYPE = ? AND DAY = ?");
                psseatavail.setString(1,trainid);
                psseatavail.setString(2,sourceid);
                psseatavail.setString(3,seattype);
                psseatavail.setString(4,dateoftravel);
                ResultSet rsseatavail = psseatavail.executeQuery();
                rsseatavail.next();
                int srcstationseats = rsseatavail.getInt("seatsavailable") + 1 ;

                psseatavail.setString(2,destid);
                rsseatavail = psseatavail.executeQuery();
                rsseatavail.next();
                int deststationseats = rsseatavail.getInt("seatsavailable") + 1 ;


                int minseatcount = (srcstationseats>deststationseats)?deststationseats:srcstationseats;

                if(srcstationseats<1 || deststationseats <1){
                    new MysqlConnectionUtil().updateStatus(pnr);
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

                    new MysqlConnectionUtil().insertPassengers(pnr,passenger[i],Integer.valueOf(seatno[i]),age[i],gender[i],tstatus);
                }
                log.info("passenger status "+status);
                rs = new MysqlConnectionUtil().getTrainName(trainid);
                rs.next();
                String trainname = rs.getString("trainname");
                String trainnumber = rs.getString("trainnumber");
                rs = new MysqlConnectionUtil().getStation(trainid,sourceid);
                rs.next();
                String stime = rs.getString("stationarrtime");
                rs = new MysqlConnectionUtil().getStation(trainid,destid);
                rs.next();
                String dtime = rs.getString("stationarrtime");

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
