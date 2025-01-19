package com.example.project.ridewave.RideApp.strategies;

import com.example.project.ridewave.RideApp.entities.RideRequest;

public interface RideFareCalculationStrategy {

    double RIDE_FARE_MULTIPLIER = 10;

    double calculateFare(RideRequest rideRequest);
}
