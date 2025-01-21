package com.example.project.ridewave.RideApp.strategies;

import com.example.project.ridewave.RideApp.entities.Driver;
import com.example.project.ridewave.RideApp.entities.Payment;
import com.example.project.ridewave.RideApp.entities.enums.PaymentStatus;
import com.example.project.ridewave.RideApp.entities.enums.TransactionMethod;
import com.example.project.ridewave.RideApp.repositories.PaymentRepository;
import com.example.project.ridewave.RideApp.services.PaymentService;
import com.example.project.ridewave.RideApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


// Strategy for handling the cash payment
@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy{

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        // Getting the driver who is processing the payment
        Driver driver = payment.getRide().getDriver();

        // setting the platform commission fee
        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        // Deducting money from the wallet
        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission, null,
                payment.getRide() , TransactionMethod.RIDE );

        // Confirming the payment
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);

        // Saving the payment details
        paymentRepository.save(payment);
    }
}
