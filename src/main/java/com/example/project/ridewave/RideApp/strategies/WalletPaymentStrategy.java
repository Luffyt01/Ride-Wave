package com.example.project.ridewave.RideApp.strategies;

import com.example.project.ridewave.RideApp.entities.Driver;
import com.example.project.ridewave.RideApp.entities.Payment;
import com.example.project.ridewave.RideApp.entities.Rider;
import com.example.project.ridewave.RideApp.entities.enums.PaymentStatus;
import com.example.project.ridewave.RideApp.entities.enums.TransactionMethod;
import com.example.project.ridewave.RideApp.repositories.PaymentRepository;
import com.example.project.ridewave.RideApp.services.PaymentService;
import com.example.project.ridewave.RideApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Rider had 232, Driver had 500
// Ride cost is 100, commission  = 30
// Rider -> 232-100 = 132
// Driver -> 500 + (100 - 30) = 570


// Strategy for handling wallet related payment
@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy{

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {
        // Getting the driver attached to the payment
        Driver driver = payment.getRide().getDriver();
        // Getting the rider attached to the payment
        Rider rider = payment.getRide().getRider();

        // Deducting the payment form the rider wallet
        walletService.deductMoneyFromWallet(rider.getUser(),
                payment.getAmount(),
                null,
                payment.getRide(),
                TransactionMethod.RIDE);

        // Calculating the amount that should be given to the driver after deducting the platform fee
        double driversCut = payment.getAmount() * (1 - PLATFORM_COMMISSION);

        // Adding money to driver wallet
        walletService.addMoneyToWallet(driver.getUser(),
                driversCut,
                null,
                payment.getRide(),
                TransactionMethod.RIDE);


    }
}
