package com.anums.blogApp.DiscussPoint.Configuration;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailure implements AuthenticationFailureHandler {

   

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        // TODO Auto-generated method stub
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        exception.printStackTrace();
        String jsonPayload = "{\"message\" : \"%s\", \"timestamp\" : \"%s\" }";
        response.getOutputStream()
                .println(String.format(jsonPayload, exception.getMessage(), Calendar.getInstance().getTime()));

    }
    
}
