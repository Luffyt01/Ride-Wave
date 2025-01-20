package com.example.project.ridewave.RideApp.strategies;

import com.example.project.ridewave.RideApp.entities.Payment;

public interface PaymentStrategy {

    static final Double PLATFORM_COMMISSION = 0.3;

    void processPayment(Payment payment);

}
