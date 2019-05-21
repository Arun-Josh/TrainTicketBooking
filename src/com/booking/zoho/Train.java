package com.booking.zoho;

import java.util.LinkedList;

public class Train {
    String trainid ;
    String trainnumber;
    String trainname;
    String source;
    String dest;
    String sourcetime;
    String desttime;
    LinkedList<Seats> seats;
    int srcstop ;
    int dststop ;

    public Train(String trainid, String trainnumber, String trainname, String source, String dest, String sourcetime, String desttime, LinkedList<Seats> seat, int srcstop, int dststop) {
        this.trainnumber = trainnumber;
        this.trainname = trainname;
        this.source = source;
        this.dest = dest;
        this.sourcetime = sourcetime;
        this.desttime = desttime;
        this.seats = seat;
        this.srcstop = srcstop;
        this.dststop = dststop;
        this.trainid = trainid;
    }

    public String getTrainid() {
        return trainid;
    }

    public LinkedList<Seats> getSeats() {
        return seats;
    }

    public int getSrcstop() {
        return srcstop;
    }

    public int getDststop() {
        return dststop;
    }

    public String getTrainnumber() {
        return trainnumber;
    }

    public String getTrainname() {
        return trainname;
    }

    public String getSource() {
        return source;
    }

    public String getDest() {
        return dest;
    }

    public String getSourcetime() {
        return sourcetime;
    }

    public String getDesttime() {
        return desttime;
    }
}