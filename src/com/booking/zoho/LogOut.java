package com.booking.zoho;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogOut extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Cookie ck[] = request.getCookies();

            System.out.println("inSIDE LOGOUT ");

//            if(ck.length==0) response.sendRedirect("index.html");

            for(Cookie c : ck){
                c.setValue(null);
                c.setMaxAge(1);
                response.addCookie(c);
            }

            HttpSession session = request.getSession();
            System.out.println("Before invalidation  "+ session.getAttribute("mail"));
            session.setAttribute("mail",null);
            System.out.println("Nullified  mail is "+ session.getAttribute("mail"));
            session.invalidate();
//            System.out.println("After invalidation  "+ session.getAttribute("mail"));
            System.out.println("---------LOGGED OUT---------");
            response.sendRedirect("index.html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
