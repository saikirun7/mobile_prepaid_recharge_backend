package com.mobileplan.mobileplan_2.controller;

import com.mobileplan.mobileplan_2.entity.MobilePlan;
import com.mobileplan.mobileplan_2.service.MobilePlanService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mobileplan")
@CrossOrigin("*")
public class MobilePlanController {

    @Autowired
    private MobilePlanService mobilePlanService;

    @PostMapping("/addUserPlan")
    public ResponseEntity<?> addUserPlan(@RequestAttribute("user") Claims claims, @RequestBody MobilePlan mobilePlan) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Logging to verify the incoming data
            System.out.println("Received MobilePlan: " + mobilePlan);
            System.out.println("Extracted Email from Claims: " + claims.getSubject());

            // Assuming the email from claims is to be set to the MobilePlan object
            mobilePlan.setEmail(claims.getSubject());

            mobilePlanService.addMobilePlan(mobilePlan);
            response.put("message", "Plan added successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllPlans")
    public ResponseEntity<Map<String, Object>> getAllPlans(@RequestAttribute("user") Claims claims) {
        List<MobilePlan> allPlans = mobilePlanService.getAllMobilePlans();
        Map<String, Object> response = new HashMap<>();

        if (allPlans.isEmpty()) {
            response.put("message", "No plans available");
        } else {
            response.put("plans", allPlans);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
