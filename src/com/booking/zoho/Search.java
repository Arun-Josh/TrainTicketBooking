package com.booking.zoho;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try{
            String source = request.getParameter("from");
            String dest   = request.getParameter("to");
            String date = request.getParameter("date");

            ServletContext sc = getServletContext();
            sc.setAttribute("date",date);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");
            PreparedStatement ps = con.prepareStatement("SELECT * From STATIONNAMES WHERE STATIONNAME = ?");
            ps.setString(1,source);
            ResultSet rs = ps.executeQuery();
            String sourceid = "";
            if(rs.next()){
                sourceid = rs.getString("stationid");
            }
            else{
                out.print(" SOURCE STATION NOT AVAILABLE");
                return;
            }

            ps = con.prepareStatement("SELECT * FROM STATIONNAMES WHERE STATIONNAME = ?");
            ps.setString(1,dest);
            rs = ps.executeQuery();
            String destid = "";
            if(rs.next()){
                destid = rs.getString("stationid");
            }
            else{
                out.print("DESTINATION STATION NOT AVAILABLE");
                return;
            }

            log.info("Source : " + sourceid + " Dest : "+ destid);

            ps = con.prepareStatement("SELECT * FROM STATIONS WHERE STATIONID = ?");
            ps.setString(1,sourceid);
            rs = ps.executeQuery();

            ArrayList<Train> trains = new ArrayList<Train>();

            while(rs.next()){
                String sourcetime = rs.getString("stationarrtime");
                int t2stopno = rs.getInt("stopno");
                ps = con.prepareStatement("SELECT  * FROM STATIONS WHERE TRAINID= ? AND STATIONID = ?");
                ps.setString(1,rs.getString("trainid"));
                ps.setString(2,destid);

                ResultSet rs1 = ps.executeQuery();
                if(rs1.next()){

                    String trainid = rs1.getString("trainid");
                    String desttime = rs1.getString("stationarrtime");
                    int t1stopno =  rs1.getInt("stopno");

                    new TrainManipulation().insertTrainIfNot(trainid,sourceid,destid,date);

                    PreparedStatement ps1 = con.prepareStatement("SELECT * FROM TRAINNAMES WHERE TRAINID = ? ");
                    ps1.setString(1,trainid);
                    ResultSet rs2 = ps1.executeQuery();
                    if(rs2.next()){
                        String trainnumber = rs2.getString("trainnumber");
                        String trainname = rs2.getString("trainname");
//trains.add(new Train(rs.getString("trainnumber"),rs.getString("trainname"),source,dest,time[tstime],time[tdtime],rs.getInt("totalseats"),trs.getInt("remseats")));
                        LinkedList<Seats> seat = new LinkedList();

                        PreparedStatement ps3 = con.prepareStatement("SELECT * FROM SEATSAVAILABLE WHERE TRAINID = ? AND DAY = ? AND STATIONID = ?");
                        ps3.setString(1,trainid);
                        ps3.setString(2,date);
                        ps3.setString(3,sourceid);
                        ResultSet rs3 = ps3.executeQuery();
                        Boolean seatvail = false;

                        while(rs3.next()){
                            seatvail = true;
                            String seattype = rs3.getString("seattype");
                            int seatcount = rs3.getInt("seatsavailable");
                            seat.add( new Seats(seattype,seatcount));
                        }

                        if(t1stopno > t2stopno && seatvail){
                            trains.add(new Train(trainid, trainnumber,trainname,source, dest, sourcetime, desttime, seat, t1stopno,t2stopno));
                            log.info("tstime = "+sourcetime +" tdtime = " +desttime+ " train no  = "+ trainnumber +" trainn name = " + trainname + " source "+source + "dest "+ dest + "\n");
                        for(int i=0;i < seat.size();i++){
                            log.info("SEAT TYPE "+ seat.get(i).seattype);
                            log.info("SEAT COUNT "+ seat.get(i).seatcount);
                        }
                            log.info("tttid"+ trainid);
                        }

                    }
                }
            }

            request.setAttribute("trains",trains);
            HttpSession session = request.getSession(true);
            request.getRequestDispatcher("searchresult.jsp").forward(request,response);


//            ResultSet rs = ps.executeQuery();
//
//            Boolean status = false;
//
//
//            ArrayList<Train> trains = new ArrayList<Train>();
//            while(rs.next()){
//                status = true;
//                boolean routestatus = false;
//
//                String route[] =  rs.getString("route").split(",");
//
//                int tstime = 0;
//                int tdtime = 0;
//
//                for(int i=0;i<route.length;i++){
//                    if(route[i].equals(source)){
//                        tstime = i;
//                        for(int j=i+1;j<route.length;j++){
//                            if( route[j].equals(dest)){
//                                tdtime = j;
//                                routestatus = true;
//                                break;
//                            }
//                        }
//                        if(routestatus) break;
//                    }
//                }
//
//
//                if(routestatus){
//                    ps = con.prepareStatement("SELECT * from trains WHERE trainnumber = ? and date = ?");
//                    ps.setString(1,rs.getString("trainnumber"));
//                    ps.setString(2,date);
//
//                    ResultSet trs = ps.executeQuery();
//                    trs.next();
//
//                    String[] time = rs.getString("stationtime").split(",");
//                    log.info("tstime = "+tstime +" tdtime = " +tdtime+ " time[tstime] = "+ time[tstime]+" time[tdtime] = " + time[tdtime] + "\n");
//                    trains.add(new Train(rs.getString("trainnumber"),rs.getString("trainname"),source,dest,time[tstime],time[tdtime],rs.getInt("totalseats"),trs.getInt("remseats")));
//                }
//            }
//            request.setAttribute("trains",trains);
//            request.getRequestDispatcher("searchresult.jsp").forward(request,response);
//           if(!status){
//                out.print("Train Not Found !!");
//            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
