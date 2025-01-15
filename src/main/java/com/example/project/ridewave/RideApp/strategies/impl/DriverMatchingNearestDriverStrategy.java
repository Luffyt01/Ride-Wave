package com.example.project.ridewave.RideApp.strategies.impl;

import com.example.project.ridewave.RideApp.dto.RideRequestDTO;
import com.example.project.ridewave.RideApp.entities.Driver;
import com.example.project.ridewave.RideApp.strategies.DriverMatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {

    @Override
    public List<Driver> findMatchingDriver(RideRequestDTO rideRequestDTO) {
        return List.of();
    }

}
