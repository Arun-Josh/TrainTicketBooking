package com.booking.zoho;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;

@WebServlet("/search")
public class Search extends HttpServlet {
    Logger log = Logger.getLogger(Search.class.getName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final MysqlConnectionUtil mysqlDB = new MysqlConnectionUtil();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        boolean access = new Validations().searchValidation(request);

        if(!access){
            new Validations().redirectToSearch(response);
            return;
        }
        try{
            String source = request.getParameter("from");
            String dest   = request.getParameter("to");
            String date = request.getParameter("date");
            System.out.println("FROM "+source+" TO "+dest +" date "+date);

            if(source.length()>30 || dest.length()>30 || (source.equals(dest))){
                out.print("");
                return;
            }

            ArrayList<Train> trains = new ArrayList();
            ResultSet rs = mysqlDB.getTrainsBetween(source,dest);

            while(rs.next()){
               String sourceid = rs.getString("fromstationid");
               String destid = rs.getString("tostationid");
               log.info("Source : " + sourceid + " Dest : "+ destid);
               String sourcetime = rs.getString("fromstationarrtime");
               int t2stopno = rs.getInt("fromstationstopno");
               String trainid = rs.getString("trainid");
               String desttime = rs.getString("tostationarrtime");
               int t1stopno =  rs.getInt("tostationstopno");
               new TrainManipulation().insertTrainIfNot(trainid,date);

               log.info("modification success");
               log.info("from TRAIN arr time"+ rs.getString("fromstationarrtime"));
               log.info("to TRAIN arr time"+ rs.getString("tostationarrtime"));

                String trainnumber = rs.getString("trainnumber");
                String trainname = rs.getString("trainname");

                LinkedList<Seats> seat = new LinkedList();

                ResultSet rs3 = mysqlDB.getAvailableSeats(trainid,date,sourceid);
                Boolean seatvail = false;

                while(rs3.next()){
                    seatvail = true;
                    String seattype = rs3.getString("seattype");
                    int seatcount = rs3.getInt("seatsavailable");
                    seat.add( new Seats(seattype,seatcount));
                }

                if(seatvail){
                    Train train = new Train(trainid, trainnumber,trainname,source, dest, sourcetime, desttime, seat, t1stopno,t2stopno, new MysqlConnectionUtil().getRoute(trainid));
                    trains.add(train);

                    log.info("tstime = "+sourcetime +" tdtime = " +desttime+ " train no  = "+ trainnumber +" trainn name = " + trainname + " source "+source + "dest "+ dest + "\n");
                for(int i=0;i < seat.size();i++){
                    log.info("SEAT TYPE "+ seat.get(i).seattype);
                    log.info("SEAT COUNT "+ seat.get(i).seatcount);
                }
                    log.info("tttid"+ trainid);
                }
            }
            String trainsJSON = new Gson().toJson(trains);
            request.setAttribute("trainsJSON",trainsJSON );

            System.out.println("trains json" + trainsJSON);
            out.print(trainsJSON);
            log.info("FORWARDED TO Reserve Trains");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("mail")==null){
//            response.sendRedirect("index.html");
            return;
        }
        else{
            doPost(request, response);
        }
    }
}
