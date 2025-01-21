package com.example.project.ridewave.RideApp.services.impl;

import com.example.project.ridewave.RideApp.entities.Driver;
import com.example.project.ridewave.RideApp.entities.Rider;
import com.example.project.ridewave.RideApp.repositories.RatingRepository;
import com.example.project.ridewave.RideApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Override
    public void rateDriver(Driver driver, Integer rating) {

    }

    @Override
    public void rateRider(Rider rider, Integer rating) {

    }
}
