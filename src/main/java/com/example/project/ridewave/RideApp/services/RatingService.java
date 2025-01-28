package com.example.project.ridewave.RideApp.services;

import com.example.project.ridewave.RideApp.dto.DriverDTO;
import com.example.project.ridewave.RideApp.dto.RiderDTO;
import com.example.project.ridewave.RideApp.entities.Ride;

public interface RatingService {

    DriverDTO rateDriver(Ride ride, Integer rating);

    RiderDTO rateRider(Ride ride, Integer rating);

    void createNewRating(Ride ride);
}
