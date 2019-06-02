package com.booking.zoho;

import java.sql.ResultSet;

public class Validations {
    protected final boolean cancelSeat(String passengerid, String userid){
        final MysqlConnectionUtil mysqlDB = new MysqlConnectionUtil();
        try{

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
