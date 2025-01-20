package com.example.project.ridewave.RideApp.dto;

import com.example.project.ridewave.RideApp.entities.Ride;
import com.example.project.ridewave.RideApp.entities.Wallet;
import com.example.project.ridewave.RideApp.entities.enums.TransactionMethod;
import com.example.project.ridewave.RideApp.entities.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTransactionDTO {
    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private RideDTO ride;

    private String transactionId;

    private WalletDTO wallet;

    private LocalDateTime timeStamp;
}
