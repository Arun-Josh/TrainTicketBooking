package com.booking.zoho;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

@WebServlet("/reserveseats")
public class ReserveSeat extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
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

                if(userid == null){
                    request.getRequestDispatcher("index.jsp").forward(request,response);
                }

                Logger log = Logger.getLogger(ReserveSeat.class.getName());
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
                String seatno[] = new String[seats];

                for(int i=0;i<seats;i++){
                    ps = con.prepareStatement("INSERT INTO PASSENGERINFO(PNR, PASSENGERNAME, SEATNO, AGE, GENDER) VALUES(?,?,?,?,?)");
                    ps.setString(1,pnr);
                    ps.setString(2,passenger[i]);
                    ps.setString(3,seatno[i]);
                    ps.setString(4,age[i]);
                    ps.setString(5,gender[i]);
                    ps.executeUpdate();
                }


            }
            catch (Exception E){
                E.printStackTrace();
            }

            request.getRequestDispatcher("TicketInfo.jsp").include(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
