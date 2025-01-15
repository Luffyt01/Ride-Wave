package com.example.project.ridewave.RideApp.services;

import com.example.project.ridewave.RideApp.dto.DriverDTO;
import com.example.project.ridewave.RideApp.dto.RideDTO;
import com.example.project.ridewave.RideApp.dto.RideRequestDTO;
import com.example.project.ridewave.RideApp.dto.RiderDTO;

import java.util.List;

public interface RiderService {

    RideRequestDTO requestRide(Long rideId);

    RideDTO cancelRide(Long rideId);


    DriverDTO rateDriver(Long rideId, Integer rating);

    RiderDTO getMyProfile();

    List<RideDTO> getAllMyRides();
}
