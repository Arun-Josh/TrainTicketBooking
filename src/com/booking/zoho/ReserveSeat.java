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

                PreparedStatement ps = con.prepareStatement("SELECT * FROM STATIONNAMES WHERE STATIONNAME = ?");
                ps.setString(1,source);
                ResultSet rs = ps.executeQuery();
                rs.next();
                String sourceid = rs.getString("stationid");

                ps.setString(1,dest);
                rs = ps.executeQuery();
                rs.next();
                String destid   = rs.getString("stationid");
                log.info("ffare " +fare);
                ps = con.prepareStatement("INSERT INTO BOOKINGS(USERID, TRAINID, MAILID, MODEOFPAYMENT, PAYMENTSTATUS, ACCOUNTNUMBER, IFSCCODE, CARDNUMBER, TICKETSTATUS, DATEOFTRAVEL, SOURCE, DEST, FARE ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ");
                ps.setString(1,userid);
                ps.setString(2,trainid);
                ps.setString(3,mailid);
                ps.setString(4,modeofpayment);
                ps.setString(5,paymentstatus);
                ps.setString(6,accountnumber);
                ps.setString(7,ifsccode);
                ps.setString(8,cardnumber);
                ps.setString(9,ticketstatus);
                ps.setString(10,dateoftravel);
                ps.setString(11,sourceid);
                ps.setString(12,destid);
                ps.setString(13,fare);
                ps.executeUpdate();

                ps = con.prepareStatement("SELECT * FROM BOOKINGS WHERE MAILID = ? AND TRAINID = ?");
                ps.setString(1,mailid);
                ps.setString(2,trainid);
                rs = ps.executeQuery();

                while(rs.next()){}
                rs.previous();
                String pnr = rs.getString("pnr");

//                sc.setAttribute("pnr",pnr);

//                int seats = (Integer) sc.getAttribute("seatcount");
                int seats = Integer.valueOf((String)request.getAttribute("seatcount"));
                log.info("seaats : "+ seats);
                System.out.println("passenegers in reserve "+session.getAttribute("passengers"));
                String passenger[] = ((String) session.getAttribute("passengers")).split(",");
                String age[] = ((String) session.getAttribute("ages")).split(",");
                String gender[] = ((String) session.getAttribute("gender")).split(",");

//                String seatno[] = new String[seats];

                String seatnumbers = "";
                String srcstopno = (String) request.getAttribute("srcstopno");
                String dststopno = (String) request.getAttribute("dststopno");

                log.info("src dst iinfo " + srcstopno + dststopno );

                PreparedStatement psstations = con.prepareStatement("SELECT  * FROM STATIONS WHERE TRAINID = ? AND STOPNO >= ? AND STOPNO <= ? ");
                psstations.setString(1,trainid);
                psstations.setString(2,dststopno);
                psstations.setString(3,srcstopno);
                ResultSet rsstations = psstations.executeQuery();

                while(rsstations.next()){

                    String stationid = rsstations.getString("STATIONID");
                    log.info("st id " + stationid);
                        log.info("seattype "+ seattype);
                        PreparedStatement psavail = con.prepareStatement("SELECT * FROM SEATSAVAILABLE WHERE TRAINID = ? AND STATIONID = ? AND SEATTYPE = ? AND DAy = ?");
                        psavail.setString(1,trainid);
                        psavail.setString(2,stationid);
                        psavail.setString(3,seattype);
                        psavail.setString(4,dateoftravel);
                        ResultSet rsavail = psavail.executeQuery();

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
                    PreparedStatement psbook = con.prepareStatement("    UPDATE BOOKINGS SET TICKETSTATUS = \"WAITING LIST\" WHERE PNR = ?");
                    psbook.setString(1,pnr);
                    psbook.executeUpdate();
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

                    ps = con.prepareStatement("INSERT INTO PASSENGERINFO(PNR, PASSENGERNAME, SEATNO, AGE, GENDER,STATUS) VALUES(?,?,?,?,?,?)");
                    ps.setString(1,pnr);
                    ps.setString(2,passenger[i]);
                    ps.setInt(3,Integer.valueOf(seatno[i]));
                    ps.setString(4,age[i]);
                    ps.setString(5,gender[i]);
                    ps.setString(6,tstatus);
                    ps.executeUpdate();
                }
                log.info("passenger status "+status);
                ps = con.prepareStatement("SELECT  * FROM TRAINNAMES WHERE TRAINID = ?");
                ps.setString(1,trainid);
                rs = ps.executeQuery();
                rs.next();
                String trainname = rs.getString("trainname");
                String trainnumber = rs.getString("trainnumber");

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
                ticket.put("fare", fare );
                ticket.put("dateoftravel", dateoftravel );
                ticket.put("status",status);

                System.out.println("Passneger s "+ticket.getString("passengers"));
                out.print(ticket);
            }
            catch (Exception E){
                E.printStackTrace();
            }

//        request.getRequestDispatcher("TicketInfo.jsp").forward(request,response);

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
