package com.example.project.ridewave.RideApp.dto;

import com.example.project.ridewave.RideApp.entities.Rider;
import com.example.project.ridewave.RideApp.entities.enums.PaymentMethod;
import com.example.project.ridewave.RideApp.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDTO {

    private Long id;
    private Point pickupLocation;
    private Point dropOffLocation;
    private LocalDateTime requestedTime;
    private RiderDTO rider;
    private PaymentMethod paymentMethod;
    private RideRequestStatus rideRequestStatus;
}
