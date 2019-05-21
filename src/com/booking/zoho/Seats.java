package com.booking.zoho;

public class Seats {
    String seattype ;
    int seatcount;

    public Seats(String seattype, int seatcount) {
        this.seattype = seattype;
        this.seatcount = seatcount;
    }

    public String getSeattype() {
        return seattype;
    }

    public int getSeatcount() {
        return seatcount;
    }
}
