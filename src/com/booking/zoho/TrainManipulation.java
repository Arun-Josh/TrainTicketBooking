package com.booking.zoho;

import java.sql.ResultSet;

public class TrainManipulation {
    public void insertTrainIfNot(String trainid,String date ){
        final MysqlConnectionUtil mysqlDB = new MysqlConnectionUtil();
        try{

            boolean trainexists = mysqlDB.checkTrain(trainid,date);

            if(trainexists){
                return;
            }

            ResultSet rsstations = mysqlDB.getStationIDByTrainID(trainid);

            while(rsstations.next()){

                String stationid = rsstations.getString("stationid");
                ResultSet rsseatsinfo = mysqlDB.getSeatsInfo(trainid);

                while(rsseatsinfo.next()){
                    String seattype = rsseatsinfo.getString("seattype");
                    String seatcount = rsseatsinfo.getString("seatcount");
                    mysqlDB.insertSeats(trainid, stationid, seattype, seatcount, date);
                }
            }
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
}
