package com.booking.zoho;

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
import java.util.logging.Logger;

@WebServlet("/reserveseats")
public class ReserveSeat extends HttpServlet {
    Logger log = Logger.getLogger(ReserveSeat.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
        PrintWriter out = response.getWriter();
            try{
                ServletContext sc = getServletContext();
                String userid = (String) sc.getAttribute("userid");
                String trainid = (String) sc.getAttribute("trainid");
                String mailid = (String) sc.getAttribute("mailid");
                String modeofpayment = (String) sc.getAttribute("modeofpayment");
                String paymentstatus = "SUCCESS";
                String accountnumber = (String) sc.getAttribute("accountnumber");
                String ifsccode = (String) sc.getAttribute("ifsccode");
                String cardnumber = (String) sc.getAttribute("cardnumber");
                String ticketstatus = "CONFIRMED";
                String dateoftravel = (String) sc.getAttribute("date");
                String source = (String) sc.getAttribute("from");
                String dest = (String) sc.getAttribute("to");
                String seattype = (String) sc.getAttribute("seattype");

                log.info("seeet" + seattype);

                if(userid == null){
                    request.getRequestDispatcher("index.jsp").forward(request,response);
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

                ps = con.prepareStatement("INSERT INTO BOOKINGS(USERID, TRAINID, MAILID, MODEOFPAYMENT, PAYMENTSTATUS, ACCOUNTNUMBER, IFSCCODE, CARDNUMBER, TICKETSTATUS, DATEOFTRAVEL, SOURCE, DEST ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ");
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
                ps.executeUpdate();

                ps = con.prepareStatement("SELECT * FROM BOOKINGS WHERE MAILID = ? AND TRAINID = ?");
                ps.setString(1,mailid);
                ps.setString(2,trainid);
                rs = ps.executeQuery();

                while(rs.next()){}
                rs.previous();
                String pnr = rs.getString("pnr");

                sc.setAttribute("pnr",pnr);

                int seats = (Integer) sc.getAttribute("seatcount");

                log.info("seaats : "+ seats);

                String passenger[] = ((String) sc.getAttribute("passengers")).split(",");
                String age[] = ((String) sc.getAttribute("ages")).split(",");
                String gender[] = ((String) sc.getAttribute("genders")).split(",");

//                String seatno[] = new String[seats];

                String seatnumbers = "";
                String srcstopno = (String) sc.getAttribute("srcstopno");
                String dststopno = (String) sc.getAttribute("dststopno");

                log.info("src dst iinfo " + srcstopno + dststopno );

                PreparedStatement psstations = con.prepareStatement("SELECT  * FROM STATIONS WHERE TRAINID = ? AND STOPNO >= ? AND STOPNO <= ? ");
                psstations.setString(1,trainid);
                psstations.setString(2,dststopno);
                psstations.setString(3,srcstopno);
                ResultSet rsstations = psstations.executeQuery();

                while(rsstations.next()){

                    String stationid = rsstations.getString("STATIONID");
                    log.info("st id " + stationid);
//                    PreparedStatement psseatype = con.prepareStatement("SELECT * FROM SEATSINFO WHERE TRAINID = ?");
//                    psseatype.setString(1,trainid);
//                    ResultSet rsseattype = psseatype.executeQuery();
//
//                    while(rsseattype.next()){
//                        int t = 0;
//                        String seattype = rsseattype.getString("seattype");
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
//                        else {
//                            out.print("Seat not available");
//                        }

                        seatsavailable = String.valueOf(Integer.valueOf(seatsavailable) - seats) ;

                            log.info("seatsavailable after modification "+ seatsavailable + " station " + stationid);


                    PreparedStatement psseatsavail = con.prepareStatement("    UPDATE SEATSAVAILABLE SET SEATSAVAILABLE = ? WHERE TRAINID = ? AND DAY = ? AND STATIONID = ?  AND SEATTYPE = ?");
                        psseatsavail.setString(1,seatsavailable);
                        psseatsavail.setString(2,trainid);
                        psseatsavail.setString(3,dateoftravel);
                        psseatsavail.setString(4,stationid);
                        psseatsavail.setString(5,seattype);
                        psseatsavail.executeUpdate();
//                    }
                }

                PreparedStatement psseatavail = con.prepareStatement("SELECT  * FROM SEATSAVAILABLE WHERE TRAINID = ? AND STATIONID = ? AND SEATTYPE = ? AND DAY = ?");
                psseatavail.setString(1,trainid);
                psseatavail.setString(2,sourceid);
                psseatavail.setString(3,seattype);
                psseatavail.setString(4,dateoftravel);
                ResultSet rsseatavail = psseatavail.executeQuery();
                rsseatavail.next();
                int t1 = rsseatavail.getInt("seatsavailable") + 1 ;

                psseatavail.setString(2,destid);
                rsseatavail = psseatavail.executeQuery();
                rsseatavail.next();
                int t2 = rsseatavail.getInt("seatsavailable") + 1 ;

                int t3 = (t1>t2)?t2:t1;

                for(int i=0;i<seats;i++){
                    if(i!=0){
                        seatnumbers+=",";
                    }
                    seatnumbers+= t3 ;
                    t3++;
                }
                log.info("seaat nos " +seatnumbers);
                String seatno[] = seatnumbers.split(",");
                log.info("snn" + seatnumbers);
                for(int i=0;i<seats;i++){
                    ps = con.prepareStatement("INSERT INTO PASSENGERINFO(PNR, PASSENGERNAME, SEATNO, AGE, GENDER) VALUES(?,?,?,?,?)");
                    ps.setString(1,pnr);
                    ps.setString(2,passenger[i]);
                    ps.setInt(3,Integer.valueOf(seatno[i]));
                    ps.setString(4,age[i]);
                    ps.setString(5,gender[i]);
                    ps.executeUpdate();
                }

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



                request.setAttribute("pnr",pnr);
                sc.setAttribute("trainid",trainid);
                sc.setAttribute("trainnumber",trainnumber);
                sc.setAttribute("trainname",trainname);
                sc.setAttribute("source",sourceid);
                sc.setAttribute("dest",destid);
                sc.setAttribute("dtime",dtime);
                sc.setAttribute("stime",stime);
                sc.setAttribute("passengers",sc.getAttribute("passengers"));
                sc.setAttribute("ages",sc.getAttribute("ages"));
                sc.setAttribute("genders",(String) sc.getAttribute("genders"));
                request.setAttribute("ticketstatus",ticketstatus);
                request.setAttribute("seatnos",seatnumbers);
                request.setAttribute("seatcount", seats );

            }
            catch (Exception E){
                E.printStackTrace();
            }

        request.getRequestDispatcher("TicketInfo.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
