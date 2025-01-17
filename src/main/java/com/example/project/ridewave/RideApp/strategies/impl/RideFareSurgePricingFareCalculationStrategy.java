package com.example.project.ridewave.RideApp.strategies.impl;

import com.example.project.ridewave.RideApp.entities.RideRequest;
import com.example.project.ridewave.RideApp.strategies.RideFareCalculationStrategy;
import org.springframework.stereotype.Service;

@Service
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {

    @Override
    public double calculateFare(RideRequest rideRequest) {
        return 0;
    }
}
