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
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try{
            String source = request.getParameter("from");
            String dest   = request.getParameter("to");
            String date = request.getParameter("date");
            System.out.println("FROM "+source+" TO "+dest +" date "+date);
//            ServletContext sc = getServletContext();
//            sc.setAttribute("date",date);

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
                            Train train = new Train(trainid, trainnumber,trainname,source, dest, sourcetime, desttime, seat, t1stopno,t2stopno);
//                            JSONObject trainJSON = new JSON
                            trains.add(train);

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
            String trainsJSON = new Gson().toJson(trains);
//            request.setAttribute("trains",trains);
//            JSONArray jsontrains = new JSONArray(trains);
            request.setAttribute("trainsJSON",trainsJSON );

            System.out.println("trains json" + trainsJSON);
            out.print(trainsJSON);
//            HttpSession session = request.getSession(true);
            log.info("FORWARDED TO Reserve Trains");
//            request.getRequestDispatcher("searchresult.jsp").forward(request,response);



        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("mail")==null){
            response.sendRedirect("index.html");
        }
        else{
            doPost(request, response);
        }
    }
}
