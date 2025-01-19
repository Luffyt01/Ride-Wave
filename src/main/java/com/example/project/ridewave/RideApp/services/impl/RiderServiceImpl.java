package com.example.project.ridewave.RideApp.services.impl;

import com.example.project.ridewave.RideApp.dto.DriverDTO;
import com.example.project.ridewave.RideApp.dto.RideDTO;
import com.example.project.ridewave.RideApp.dto.RideRequestDTO;
import com.example.project.ridewave.RideApp.dto.RiderDTO;
import com.example.project.ridewave.RideApp.entities.Driver;
import com.example.project.ridewave.RideApp.entities.RideRequest;
import com.example.project.ridewave.RideApp.entities.Rider;
import com.example.project.ridewave.RideApp.entities.User;
import com.example.project.ridewave.RideApp.entities.enums.RideRequestStatus;
import com.example.project.ridewave.RideApp.exceptions.ResourceNotFoundException;
import com.example.project.ridewave.RideApp.repositories.RideRequestRepository;
import com.example.project.ridewave.RideApp.repositories.RiderRepository;
import com.example.project.ridewave.RideApp.services.RiderService;
import com.example.project.ridewave.RideApp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {


    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideStrategyManager rideStrategyManager;
    private final ModelMapper modelMapper;

    @Override
    @Transactional // using transactional will not make any change if any error occur in the function
                   // it will roll back to previous state
                   // it's like either all transaction happens or noting happens
    public RideRequestDTO requestRide(RideRequestDTO rideRequestDTO) {
        // Get the current logged in rider
        Rider rider = getCurrentRider();
        // Create a ride request for the rider
        RideRequest rideRequest = modelMapper.map(rideRequestDTO, RideRequest.class);
        // Setting the ride request status to pending as the rider has yet to confirm the ride
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        // setting the rider for the request
        rideRequest.setRider(rider);

        // Generating the fare to the rider
        // According to the pickup location and the drop off location
        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);
        // Saving the ride request
        RideRequest savedRideRequest =rideRequestRepository.save(rideRequest);

        // Getting all the drivers available for the request
        List<Driver> drivers = rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDrivers(rideRequest);

        return modelMapper.map(savedRideRequest, RideRequestDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDTO rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDTO getMyProfile() {
        return null;
    }

    @Override
    public List<RideDTO> getAllMyRides() {
        return List.of();
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider.builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        return riderRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not found with id: "+ 1L
        ));
    }
}
