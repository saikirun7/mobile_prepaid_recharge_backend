package com.authapi.authapi_1.service;

import com.authapi.authapi_1.entity.Admin;
import com.authapi.authapi_1.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    public String userToken(String mobile) throws ServletException {
        if (mobile == null) {
            throw new ServletException("Mobile number cannot be empty");
        }

        // Validate the user by mobile number
        User user = userService.getUserByMobile(mobile);
        if (user == null) {
            throw new ServletException("User not found with this mobile number");
        }

        // Retrieve email from the user object
        String email = user.getEmail();
        if (email == null) {
            throw new ServletException("Email not found for this mobile number");
        }

        // Generate JWT token with email as the subject
        return Jwts.builder()
                .setSubject(email) // Set the subject to the user's email
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
                .signWith(SignatureAlgorithm.HS256, "MobileAppSecretKey")
                .compact();
    }


    public String generateToken(String email, String password) throws ServletException {
        if (email == null || password == null) {
            throw new ServletException("Email or Password cannot be empty");
        }

        // Validate the admin by email and password
        Admin admin = adminService.validateUserByEmail(email);
        if (admin == null || !admin.getPassword().equals(password)) {
            throw new ServletException("Invalid credentials");
        }

        // Generate JWT token
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
                .signWith(SignatureAlgorithm.HS256, "MobileAppSecretKey")
                .compact();
    }
}
