package com.mobileplan.mobileplan_2.service;

import com.mobileplan.mobileplan_2.entity.MobilePlan;

import java.util.List;

public interface MobilePlanService {
    MobilePlan addMobilePlan(MobilePlan mobilePlan);
    List<MobilePlan> getAllMobilePlans();
}
