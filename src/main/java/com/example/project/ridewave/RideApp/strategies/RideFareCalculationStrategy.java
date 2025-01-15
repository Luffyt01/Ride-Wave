package com.example.project.ridewave.RideApp.strategies;

import com.example.project.ridewave.RideApp.dto.RideRequestDTO;

public interface RideFareCalculationStrategy {

    double calculateFare(RideRequestDTO rideRequestDTO);
}
