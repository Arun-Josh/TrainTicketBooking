package com.booking.zoho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Validations {
    protected final boolean cancelSeat(String passengerid, String userid){
        final MysqlConnectionUtil mysqlDB = new MysqlConnectionUtil();
        try{
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");
//            PreparedStatement ps = con.prepareStatement("SELECT * FROM BOOKINGS INNER JOIN PASSENGERINFO ON BOOKINGS.PNR = PASSENGERINFO.PNR WHERE PASSENGERID = ? AND USERID = ?");
//            ps.setString(1,passengerid);
//            ps.setString(2,userid);
//            ResultSet rs = ps.executeQuery();
            ResultSet rs = mysqlDB.validateTicketAuthority(passengerid,userid);
            if(rs.next()){
                return true;
            }
        }catch (Exception E){
            E.printStackTrace();
        }
        return false;
    }
}
