package com.booking.zoho;

import java.util.LinkedList;

public class Tickets {
    String passengerid;
    String pnr;
    String from;
    String to;
    String trainnumber;
    String trainname;
    String ticketstatus;
    String dateoftravel;
    String ticketfare;
    String passenger;
    String age;
    String seatno;
    String gender;
    String stime;
    String dtime;
    String seattype;


    public Tickets(String passengerid, String pnr, String from, String to, String trainnumber, String trainname, String ticketstatus, String dateoftravel, String ticketfare, String passenger, String age, String seatno, String gender, String stime, String dtime, String seattype) {
        this.passengerid = passengerid;
        this.pnr = pnr;
        this.from = from;
        this.to = to;
        this.trainnumber = trainnumber;
        this.trainname = trainname;
        this.ticketstatus = ticketstatus;
        this.dateoftravel = dateoftravel;
        this.ticketfare = ticketfare;
        this.passenger = passenger;
        this.age = age;
        this.seatno = seatno;
        this.gender = gender;
        this.stime = stime;
        this.dtime = dtime;
        this.seattype = seattype;
    }



    public String getPassengerid() {
        return passengerid;
    }

    public String getStime() {
        return stime;
    }

    public String getDtime() {
        return dtime;
    }

    public String getPnr() {
        return pnr;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getTrainnumber() {
        return trainnumber;
    }

    public String getTrainname() {
        return trainname;
    }

    public String getTicketstatus() {
        return ticketstatus;
    }

    public String getDateoftravel() {
        return dateoftravel;
    }

    public String getTicketfare() {
        return ticketfare;
    }

    public String getPassenger() {
        return passenger;
    }

    public String getAge() {
        return age;
    }

    public String getSeatno() {
        return seatno;
    }

    public String getGender() {
        return gender;
    }
}