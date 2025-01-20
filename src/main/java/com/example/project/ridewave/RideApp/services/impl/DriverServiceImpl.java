package com.example.project.ridewave.RideApp.services.impl;

import com.example.project.ridewave.RideApp.dto.DriverDTO;
import com.example.project.ridewave.RideApp.dto.RideDTO;
import com.example.project.ridewave.RideApp.entities.Driver;
import com.example.project.ridewave.RideApp.entities.Ride;
import com.example.project.ridewave.RideApp.entities.RideRequest;
import com.example.project.ridewave.RideApp.entities.enums.RideRequestStatus;
import com.example.project.ridewave.RideApp.entities.enums.RideStatus;
import com.example.project.ridewave.RideApp.exceptions.ResourceNotFoundException;
import com.example.project.ridewave.RideApp.repositories.DriverRepository;
import com.example.project.ridewave.RideApp.services.DriverService;
import com.example.project.ridewave.RideApp.services.PaymentService;
import com.example.project.ridewave.RideApp.services.RideRequestService;
import com.example.project.ridewave.RideApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;

    @Override
    public RideDTO acceptRide(Long rideRequestId) {
        // getting the ride request
        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);

        // Check if the ride request is on PENDING status
        // if the ride is CANCELLED or CONFIRMED the throw exception
        if (!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("RideRequest cannot be accepted, status is "+ rideRequest.getRideRequestStatus());
        }

        // check if the current logged user is driver or not
        // and if the user is driver then get the driver details
        Driver currentDriver = getCurrentDriver();

        // checking if the current driver is available to accept the ride request
        if (!currentDriver.getAvailable()) {
            throw new RuntimeException("Driver cannot accept ride due to unavailability");
        }

        // setting the driver availability to false as driver has accepted a ride
        Driver savedDriver = updateDriverAvailability(currentDriver, false);
        Ride ride = rideService.createNewRide(rideRequest, savedDriver);

        return modelMapper.map(ride, RideDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        // Getting the ride the driver wants to cancel
        Ride ride = rideService.getRideById(rideId);

        // Getting the current logged in driver
        Driver driver = getCurrentDriver();

        // checking if the current driver is the same driver who has accepted the rideRequest
        if (!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot cancel this ride as he has not accepted it earlier");
        }

        // Check if the ride status is CONFIRMED only then can driver cancel the ride
         if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
             throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
         }

         // updating the ride status to CANCELLED
         rideService.updateRideStatus(ride, RideStatus.CANCELLED);

         // Making the driver available
         driver.setAvailable(true);
         driverRepository.save(driver);

         return modelMapper.map(ride, RideDTO.class);
    }

    @Override
    public RideDTO startRide(Long rideId, String otp) {
        // Getting the ride using the submitted ride id
        Ride ride = rideService.getRideById(rideId);
        // getting the current driver
        Driver driver = getCurrentDriver();

        // checking if the current driver is the same driver who has accepted the rideRequest
        if (!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
        }

        // Checking if the ride status is CONFIRMED
        // If ride status is CANCELED, ONGOING, ENDED then throw exception
        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride status is not CONFIRMED hence cannot be started, status: "+ride.getRideStatus());
        }

        // Verifying the otp gotten from the user and the otp entered by the driver
        if (!otp.equals(ride.getOtp())){
            throw new RuntimeException("Otp is not valid, otp: "+otp);
        }

        ride.setStartedAt(LocalDateTime.now());
        // updating the ride status to ONGOING
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);

        paymentService.createNewPayment(savedRide);

        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    @Transactional
    public RideDTO endRide(Long rideId) {
        // Getting the ride using the submitted ride id
        Ride ride = rideService.getRideById(rideId);
        // getting the current driver
        Driver driver = getCurrentDriver();

        // checking if the current driver is the same driver who has accepted the rideRequest
        if (!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
        }

        // Checking if the ride status is ONGOING
        // If ride status is CANCELED, CONFIRMED, ENDED then throw exception
        if (!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride status is not ONGOING hence cannot be ended, status: "+ride.getRideStatus());
        }

        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(driver,true);

        paymentService.processPayment(savedRide);

        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    public RideDTO rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public DriverDTO getMyProfile() {
        Driver currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver, pageRequest)
                .map(
                        ride -> modelMapper.map(ride, RideDTO.class)
                );
    }

    @Override
    public Driver getCurrentDriver() {
        return driverRepository.findById(2L)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id "+2L));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
    }
}
