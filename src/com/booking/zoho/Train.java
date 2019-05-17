package com.booking.zoho;

public class Train {
    String trainnumber;
    String trainname;
    String source;
    String dest;
    String sourcetime;
    String desttime;
    int seats;
    int remseats;

    public Train(String trainnumber, String trainname, String source, String dest, String sourcetime, String desttime, int seats, int remseats) {
        this.trainnumber = trainnumber;
        this.trainname = trainname;
        this.source = source;
        this.dest = dest;
        this.sourcetime = sourcetime;
        this.desttime = desttime;
        this.seats = seats;
        this.remseats = remseats;
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

    public int getSeats() {
        return seats;
    }

    public int getRemseats() {
        return remseats;
    }
}