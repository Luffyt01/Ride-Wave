package com.example.project.ridewave.RideApp.repositories;

import com.example.project.ridewave.RideApp.entities.Payment;
import com.example.project.ridewave.RideApp.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByRide(Ride ride);
}
