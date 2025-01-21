package com.example.project.ridewave.RideApp.services;

import com.example.project.ridewave.RideApp.entities.Driver;
import com.example.project.ridewave.RideApp.entities.Rider;

public interface RatingService {

    void rateDriver(Driver driver, Integer rating);

    void rateRider(Rider rider, Integer rating);
}
