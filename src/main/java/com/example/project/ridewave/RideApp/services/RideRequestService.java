package com.example.project.ridewave.RideApp.services;

import com.example.project.ridewave.RideApp.entities.RideRequest;

public interface RideRequestService {
    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
