package com.authapi.authapi_1.controller;

import com.authapi.authapi_1.entity.User;
import com.authapi.authapi_1.exception.UserAlreadyExistsException;
import com.authapi.authapi_1.service.AuthService;
import com.authapi.authapi_1.service.UserService;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExistsException {
        Map<String, Object> response = new HashMap<>();
        try {
            userService.addUser(user);
            response.put("message", "User registered successfully");
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
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String mobile = request.get("mobile");

        try {
            // Generate JWT token
            String token = authService.userToken(mobile);

            // Retrieve user details
            User foundUser = userService.getUserByMobile(mobile);

            // Populate response
            response.put("name", foundUser.getName());
            response.put("email", foundUser.getEmail());
            response.put("mobile", foundUser.getMobile());
            response.put("token", token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ServletException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
