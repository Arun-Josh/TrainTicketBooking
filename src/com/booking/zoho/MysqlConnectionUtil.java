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

    final public LinkedList<String> getRoute(String trainid){
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
}
