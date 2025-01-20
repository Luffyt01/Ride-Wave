package com.example.project.ridewave.RideApp.services.impl;

import com.example.project.ridewave.RideApp.entities.Payment;
import com.example.project.ridewave.RideApp.entities.Ride;
import com.example.project.ridewave.RideApp.entities.enums.PaymentStatus;
import com.example.project.ridewave.RideApp.exceptions.ResourceNotFoundException;
import com.example.project.ridewave.RideApp.repositories.PaymentRepository;
import com.example.project.ridewave.RideApp.services.PaymentService;
import com.example.project.ridewave.RideApp.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PaymentServiceImpl implements PaymentService {

    private final PaymentStrategyManager paymentStrategyManager;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Ride ride) {
        Payment payment = paymentRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for ride: "+ride.getId()));
        paymentStrategyManager.paymentStrategy(ride.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment = Payment.builder()
                .ride(ride)
                .paymentMethod(ride.getPaymentMethod())
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);
    }
}
