package com.booking.zoho;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
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

            String date = "2019-05-20";

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/trainreservation","root","root");
            PreparedStatement ps = con.prepareStatement("SELECT * From traininfo");
            ResultSet rs = ps.executeQuery();



            Boolean status = false;
            ArrayList<Train> trains = new ArrayList<Train>();
            while(rs.next()){
                status = true;
                boolean routestatus = false;

                String route[] =  rs.getString("route").split(",");

                int tstime = 0;
                int tdtime = 0;

                for(int i=0;i<route.length;i++){
                    if(route[i].equals(source)){
                        tstime = i;
                        for(int j=i+1;j<route.length;j++){
                            if( route[j].equals(dest)){
                                tdtime = j;
                                routestatus = true;
                                break;
                            }
                        }
                        if(routestatus) break;
                    }
                }


                if(routestatus){
                    ps = con.prepareStatement("SELECT * from trains WHERE trainnumber = ? and date = ?");
                    ps.setString(1,rs.getString("trainnumber"));
                    ps.setString(2,date);

                    ResultSet trs = ps.executeQuery();
                    trs.next();

                    String[] time = rs.getString("stationtime").split(",");
                    log.info("tstime = "+tstime +" tdtime = " +tdtime+ " time[tstime] = "+ time[tstime]+" time[tdtime] = " + time[tdtime] + "\n");
                    trains.add(new Train(rs.getString("trainnumber"),rs.getString("trainname"),source,dest,time[tstime],time[tdtime],rs.getInt("totalseats"),trs.getInt("remseats")));
                }
            }
            request.setAttribute("trains",trains);
            request.getRequestDispatcher("searchresult.jsp").forward(request,response);
           if(!status){
                out.print("Train Not Found !!");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
