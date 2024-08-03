package com.authapi.authapi_1.controller;

import com.authapi.authapi_1.entity.Admin;
import com.authapi.authapi_1.exception.UserAlreadyExistsException;
import com.authapi.authapi_1.exception.UserNotFoundException;
import com.authapi.authapi_1.service.AdminService;
import com.authapi.authapi_1.service.AuthService;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin) throws UserAlreadyExistsException {
        Map<String, Object> response = new HashMap<>();
        try {
            adminService.addAdmin(admin);
            response.put("message", "Admin registered successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Admin admin) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Validate the admin credentials and generate JWT token
            String token = authService.generateToken(admin.getEmail(), admin.getPassword());

            // Retrieve admin details
            Admin foundAdmin = adminService.validateUserByEmail(admin.getEmail());

            // Populate response
            response.put("name", foundAdmin.getName());
            response.put("email", foundAdmin.getEmail());
            response.put("mobile", foundAdmin.getMobile());
            response.put("token", token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (ServletException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
