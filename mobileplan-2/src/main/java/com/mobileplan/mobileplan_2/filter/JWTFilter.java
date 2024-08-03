package com.mobileplan.mobileplan_2.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JWTFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authHeader = httpRequest.getHeader("Authorization");

        if ("OPTIONS".equals(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(httpRequest, httpResponse);
        } else {
            if (authHeader == null || !authHeader.startsWith("Bearer")) {
                throw new ServletException("Missing or invalid Authorization header");
            }
            String jwtToken = authHeader.substring(7);
            Claims claims = Jwts.parser().setSigningKey("MobileAppSecretKey").parseClaimsJws(jwtToken).getBody();
            System.out.println("Claims Extracted: " + claims);
            httpRequest.setAttribute("user", claims);
            chain.doFilter(httpRequest, httpResponse);
        }
    }
}
