package com.example.project.ridewave.RideApp.strategies;

import com.example.project.ridewave.RideApp.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.example.project.ridewave.RideApp.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.example.project.ridewave.RideApp.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import com.example.project.ridewave.RideApp.strategies.impl.RiderFareDefaultFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingHighestRatedDriverStrategy highestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy;
    private final RiderFareDefaultFareCalculationStrategy defaultFareCalculationStrategy;


    // Giving a list of highly rated drivers if the user rating is above 4.8
    // else giving a list of nearest drivers
    public DriverMatchingStrategy driverMatchingStrategy(double riderRating){
        if (riderRating >= 4.8) {
            return highestRatedDriverStrategy;
        }else {
            return nearestDriverStrategy;
        }
    }

    // Calculating the ride fare
    // if the rider books a ride between 6PM to 9PM then a surge fee is added
    public RideFareCalculationStrategy rideFareCalculationStrategy(){
        // 6 PM to 9 PM

        LocalTime surgeStartTime = LocalTime.of(18,0);
        LocalTime surgeEndTime = LocalTime.of(21,0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);
        if (isSurgeTime){
            return surgePricingFareCalculationStrategy;
        }else {
            return defaultFareCalculationStrategy;
        }
    }

}
