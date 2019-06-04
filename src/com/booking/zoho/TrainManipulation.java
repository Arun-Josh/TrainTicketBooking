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
            ResultSet rs = mysqlDB.getTrainInsertData(trainid);
            while(rs.next()){
                String stationid = rs.getString("stationid");
                String seattype = rs.getString("seattype");
                String seatcount = rs.getString("seatcount");
                mysqlDB.insertSeats(trainid, stationid, seattype, seatcount, date);
            }
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
}
