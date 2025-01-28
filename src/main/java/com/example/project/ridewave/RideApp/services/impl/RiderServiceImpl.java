package com.example.project.ridewave.RideApp.services.impl;

import com.example.project.ridewave.RideApp.dto.DriverDTO;
import com.example.project.ridewave.RideApp.dto.RideDTO;
import com.example.project.ridewave.RideApp.dto.RideRequestDTO;
import com.example.project.ridewave.RideApp.dto.RiderDTO;
import com.example.project.ridewave.RideApp.entities.*;
import com.example.project.ridewave.RideApp.entities.enums.RideRequestStatus;
import com.example.project.ridewave.RideApp.entities.enums.RideStatus;
import com.example.project.ridewave.RideApp.exceptions.ResourceNotFoundException;
import com.example.project.ridewave.RideApp.repositories.RideRequestRepository;
import com.example.project.ridewave.RideApp.repositories.RiderRepository;
import com.example.project.ridewave.RideApp.services.DriverService;
import com.example.project.ridewave.RideApp.services.RatingService;
import com.example.project.ridewave.RideApp.services.RideService;
import com.example.project.ridewave.RideApp.services.RiderService;
import com.example.project.ridewave.RideApp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;

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
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if (!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider does not own this ride with id" +rideId);
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
        }
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(), true);

        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    public DriverDTO rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        // checking if the current rider is the same rider who has requested the rideRequest
        if (!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider is not the owner of this ride");
        }

        // Checking if the ride status is ENDED
        // If ride status is CANCELED, ONGOING, CONFIRMED then throw exception
        if (!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status is not ENDED hence cannot start rating, status: "+ride.getRideStatus());
        }

        return ratingService.rateDriver(ride,rating);
    }

    @Override
    public RiderDTO getMyProfile() {
        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider, RiderDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest)
                .map(
                        ride -> modelMapper.map(ride, RideDTO.class)
                );
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return riderRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Rider not associated with user with id: "+user.getId()));
    }
}
