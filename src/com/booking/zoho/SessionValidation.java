package com.booking.zoho;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionValidation {

    public boolean validate(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("mail")!=null){
            System.out.println("The user logged in is "+session.getAttribute("mail"));
            return true;
        }
        else{
            return false;
        }
    }
}
