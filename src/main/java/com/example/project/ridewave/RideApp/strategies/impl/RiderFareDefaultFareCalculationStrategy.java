package com.example.project.ridewave.RideApp.strategies.impl;

import com.example.project.ridewave.RideApp.entities.RideRequest;
import com.example.project.ridewave.RideApp.services.DistanceService;
import com.example.project.ridewave.RideApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
public class RiderFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy {


    private final DistanceService distanceService;


    @Override
    public double calculateFare(RideRequest rideRequest) {
        Double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(), rideRequest.getDropOffLocation());
        return distance * RIDE_FARE_MULTIPLIER;
    }
}
