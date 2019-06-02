package com.booking.zoho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class MysqlConnectionUtil {
    static  Connection con;
    PreparedStatement ps;

    static {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");
        }catch (Exception E){
            E.printStackTrace();
        }
    }

    final ResultSet authLogin(String mail, String pass)throws Exception{
        ps = con.prepareStatement("SELECT * FROM USERS WHERE mailid=? AND password=?");
        ps.setString(1,mail);
        ps.setString(2,pass);
        return ps.executeQuery();
    }

    final boolean checkTrain(String trainid, String date)throws Exception{
        ps = con.prepareStatement("SELECT * FROM SEATSAVAILABLE WHERE TRAINID = ? AND DAY = ?");
        ps.setString(1,trainid);
        ps.setString(2,date);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            return true;
        }
        else return false;
    }

    final ResultSet getUserByUserid(String userid)throws Exception{
        ps = con.prepareStatement("SELECT * FROM USERS where USERID = ?" );
        ps.setString(1,userid);
        return   ps.executeQuery();
    }

    final ResultSet getUserByMailID(String email)throws Exception{
        PreparedStatement pscheck = con.prepareStatement("SELECT * FROM USERS WHERE MAILID = ?");
        pscheck.setString(1,email);
        return pscheck.executeQuery();
    }

    final ResultSet getTrainNameAndNumber(String trainid) throws Exception{
        ps = con.prepareStatement("SELECT  * FROM TRAINNAMES WHERE TRAINID = ?");
        ps.setString(1,trainid);
        return ps.executeQuery();
    }

    final LinkedList<String> getRoute(String trainid){
        LinkedList<String> route = new LinkedList<>();
        try{
            ps = con.prepareStatement("SELECT STATIONNAMES.STATIONNAME FROM STATIONNAMES LEFT JOIN STATIONS ON STATIONNAMES.STATIONID = STATIONS.STATIONID WHERE TRAINID = ? ORDER BY STATIONS.STOPNO");
            ps.setString(1,trainid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                route.add(rs.getString("STATIONNAME"));
            }
        }catch (Exception E){
            E.printStackTrace();
        }
        return route;
    }

    final ResultSet getStationIDByTrainID(String trainid)throws Exception{
        ps = con.prepareStatement("SELECT * FROM STATIONS WHERE TRAINID = ?");
        ps.setString(1,trainid);
        return ps.executeQuery();
    }

    final boolean bookTicket(String userid,String trainid,String mailid,String modeofpayment,String paymentstatus,String accountnumber,String ifsccode,String cardnumber,String ticketstatus,String dateoftravel,String sourceid,String destid,String fare,String seattype) throws Exception{
        ps = con.prepareStatement("INSERT INTO BOOKINGS(USERID, TRAINID, MAILID, MODEOFPAYMENT, PAYMENTSTATUS, ACCOUNTNUMBER, IFSCCODE, CARDNUMBER, TICKETSTATUS, DATEOFTRAVEL, SOURCE, DEST, FARE, SEATTYPE ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
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
        ps.setString(14,seattype);
        ps.executeUpdate();
        return true;
    }

    final ResultSet getPnr(String mailid, String trainid) throws Exception{
        ps = con.prepareStatement("SELECT * FROM BOOKINGS WHERE MAILID = ? AND TRAINID = ?");
        ps.setString(1,mailid);
        ps.setString(2,trainid);
        return ps.executeQuery();
    }

    final ResultSet getStationByStationName(String location) throws Exception{
        ps = con.prepareStatement("SELECT * FROM STATIONNAMES WHERE STATIONNAME = ?");
        ps.setString(1,location);
        return ps.executeQuery();
    }

    final ResultSet getStationByStationID(String stationid)throws Exception{
        ps = con.prepareStatement("SELECT * FROM STATIONS WHERE STATIONID = ?");
        ps.setString(1,stationid);
        return ps.executeQuery();
    }

    final ResultSet getTravellingStations(String trainid, String dststopno, String srcstopno)throws Exception{
        ps = con.prepareStatement("SELECT  * FROM STATIONS WHERE TRAINID = ? AND STOPNO >= ? AND STOPNO < ? ");
        ps.setString(1,trainid);
        ps.setString(2,dststopno);
        ps.setString(3,srcstopno);
        return ps.executeQuery();
    }

    final ResultSet getStation(String trainid, String stationid) throws Exception{
        ps = con.prepareStatement("SELECT  * FROM STATIONS WHERE TRAINID = ? AND STATIONID = ?");
        ps.setString(1,trainid);
        ps.setString(2,stationid);
        return ps.executeQuery();
    }

    final boolean insertPassengers(String pnr,String passenger,int seatno,String age,String gender,String tstatus)throws Exception{
        ps = con.prepareStatement("INSERT INTO PASSENGERINFO(PNR, PASSENGERNAME, SEATNO, AGE, GENDER,STATUS) VALUES(?,?,?,?,?,?)");
        ps.setString(1,pnr);
        ps.setString(2,passenger);
        ps.setInt(3,seatno);
        ps.setString(4,age);
        ps.setString(5,gender);
        ps.setString(6,tstatus);
        ps.executeUpdate();
        return true;
    }

    final boolean updateStatus(String pnr) throws Exception{
        con.prepareStatement("UPDATE BOOKINGS SET TICKETSTATUS = \"WAITING LIST\" WHERE PNR = ?");
        ps.setString(1,pnr);
        ps.executeUpdate();
        return true;
    }

    final ResultSet getAvailableSeat(String trainid, String stationid, String seattype, String dateoftravel)throws Exception{
        ps = con.prepareStatement("SELECT * FROM SEATSAVAILABLE WHERE TRAINID = ? AND STATIONID = ? AND SEATTYPE = ? AND DAy = ?");
        ps.setString(1,trainid);
        ps.setString(2,stationid);
        ps.setString(3,seattype);
        ps.setString(4,dateoftravel);
        return ps.executeQuery();
    }

    final ResultSet getAvailableSeats(String trainid, String date, String sourceid)throws Exception{
        ps = con.prepareStatement("SELECT * FROM SEATSAVAILABLE WHERE TRAINID = ? AND DAY = ? AND STATIONID = ?");
        ps.setString(1,trainid);
        ps.setString(2,date);
        ps.setString(3,sourceid);
        return ps.executeQuery();
    }

    final ResultSet getSeatsInfo(String trainid)throws Exception{
        ps = con.prepareStatement("SELECT * FROM SEATSINFO WHERE TRAINID = ?");
        ps.setString(1,trainid);
        return  ps.executeQuery();
    }

    final int insertSeats(String trainid, String stationid, String seattype, String seatcount, String date)throws Exception{
        PreparedStatement psseatsavail = con.prepareStatement("INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES (?,?,?,?,?)");
        psseatsavail.setString(1,trainid);
        psseatsavail.setString(2,stationid);
        psseatsavail.setString(3,seattype);
        psseatsavail.setString(4,seatcount);
        psseatsavail.setString(5,date);
        return  psseatsavail.executeUpdate();
    }

    final ResultSet validateTicketAuthority(String passengerid, String userid)throws Exception{
        PreparedStatement ps = con.prepareStatement("SELECT * FROM BOOKINGS INNER JOIN PASSENGERINFO ON BOOKINGS.PNR = PASSENGERINFO.PNR WHERE PASSENGERID = ? AND USERID = ?");
        ps.setString(1,passengerid);
        ps.setString(2,userid);
        return ps.executeQuery();
    }

    final boolean updateAvailableSeats(String seatsavailable, String trainid, String dateoftravel, String stationid, String seattype)throws Exception{
        ps = con.prepareStatement("UPDATE SEATSAVAILABLE SET SEATSAVAILABLE = ? WHERE TRAINID = ? AND DAY = ? AND STATIONID = ?  AND SEATTYPE = ?");
        ps.setString(1,seatsavailable);
        ps.setString(2,trainid);
        ps.setString(3,dateoftravel);
        ps.setString(4,stationid);
        ps.setString(5,seattype);
        ps.executeUpdate();
        return true;
    }

    final ResultSet getBookingInfoByUserid(String userid)throws Exception{
        ps = con.prepareStatement("SELECT BOOKINGS.PNR, BOOKINGS.USERID, BOOKINGS.TRAINID,\n" +
                "                    BOOKINGS.TICKETSTATUS, BOOKINGS.DATEOFTRAVEL, BOOKINGS.SOURCE, BOOKINGS.DEST, BOOKINGS.FARE, BOOKINGS.SEATTYPE, \n" +
                "                    PASSENGERINFO.PASSENGERID, PASSENGERINFO.PASSENGERNAME, PASSENGERINFO.SEATNO, PASSENGERINFO.AGE, PASSENGERINFO.GENDER, PASSENGERINFO.STATUS\n" +
                "                    FROM BOOKINGS,PASSENGERINFO  WHERE BOOKINGS.USERID = ? AND PASSENGERINFO.PNR = BOOKINGS.PNR;");
        ps.setString(1, userid);
        return ps.executeQuery();
    }

    final ResultSet getBookingInfoByPnr(int pnr)throws Exception{
        ps = con.prepareStatement("SELECT * FROM bookings where pnr = ?" );
        ps.setInt(1,pnr);
        return ps.executeQuery();
    }

    final boolean cancelSeat(String passengerid)throws Exception{
        ps = con.prepareStatement("UPDATE PASSENGERINFO SET STATUS = \"CANCELLED\" WHERE PASSENGERID = ?");
        ps.setString(1,passengerid);
        ps.executeUpdate();
        return true;
    }

    final ResultSet getFare(String seattype, String trainid)throws Exception{
        ps = con.prepareStatement("SELECT * FROM FARES WHERE SEATTYPE = ? AND TRAINID = ?");
        ps.setString(1,seattype);
        ps.setString(2,trainid);
        return ps.executeQuery();
    }

    final ResultSet getPassengerInfo(int pnr) throws Exception{
        ps = con.prepareStatement("SELECT * FROM bookings where pnr = ?" );
        ps.setInt(1,pnr);
        return  ps.executeQuery();
    }

    final int insertUsers(String email, String phno, String uname, String gender, String pass)throws Exception{
        ps = con.prepareStatement("Insert INTO USERS(mailid,phone, name, gender,password) VALUES(?,?,?,?,?)");
        ps.setString(1,email);
        ps.setString(2,phno);
        ps.setString(3,uname);
        ps.setString(4,gender);
        ps.setString(5,pass);
        return ps.executeUpdate();
    }
}
