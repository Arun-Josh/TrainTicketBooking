package com.booking.zoho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TrainManipulation {
    public void insertTrainIfNot(String trainid, String sourceid, String destid, String date ){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM SEATSAVAILABLE WHERE TRAINID = ? AND DAY = ?");
            ps.setString(1,trainid);
            ps.setString(2,date);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return;
            }
            PreparedStatement psstations = con.prepareStatement("SELECT * FROM STATIONS WHERE TRAINID = ?");
            psstations.setString(1,trainid);
            ResultSet rsstations = psstations.executeQuery();

            while(rsstations.next()){

                String stationid = rsstations.getString("stationid");
                PreparedStatement psseatsinfo = con.prepareStatement("SELECT * FROM SEATSINFO WHERE TRAINID = ?");
                psseatsinfo.setString(1,trainid);
                ResultSet rsseatsinfo = psseatsinfo.executeQuery();

                while(rsseatsinfo.next()){
                    String seattype = rsseatsinfo.getString("seattype");
                    String seatcount = rsseatsinfo.getString("seatcount");
                    PreparedStatement psseatsavail = con.prepareStatement("INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES (?,?,?,?,?)");
                    psseatsavail.setString(1,trainid);
                    psseatsavail.setString(2,stationid);
                    psseatsavail.setString(3,seattype);
                    psseatsavail.setString(4,seatcount);
                    psseatsavail.setString(5,date);
                    psseatsavail.executeUpdate();
                }
            }
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
}
