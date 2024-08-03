package com.mobileplan.mobileplan_2.service;

import com.mobileplan.mobileplan_2.entity.MobilePlan;
import com.mobileplan.mobileplan_2.repository.MobilePlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MobilePlanServiceImpl implements MobilePlanService {

    @Autowired
    private MobilePlanRepository mobilePlanRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public MobilePlan addMobilePlan(MobilePlan mobilePlan) {
        MobilePlan savedPlan = mobilePlanRepository.save(mobilePlan);

        // Sending email
        try {
            String to = savedPlan.getEmail();
            String subject = "Mobile Recharge Successfully Confirmation";
            String text = String.format("Dear user, your mobile plan with the following details has been added successfully:\n\nMobile: %s\nPrice: %s\nValidity: %s\nData: %s",
                    savedPlan.getMobile(), savedPlan.getPrice(), savedPlan.getValidity(), savedPlan.getData());
            emailService.sendEmail(to, subject, text);
        } catch (Exception e) {
            // Log the exception (do not throw it to avoid rolling back the transaction)
            System.err.println("Error sending email: " + e.getMessage());
        }

        return savedPlan;
    }

    @Override
    public List<MobilePlan> getAllMobilePlans() {
        return mobilePlanRepository.findAll();
    }
}
