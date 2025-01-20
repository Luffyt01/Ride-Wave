package com.example.project.ridewave.RideApp.services.impl;

import com.example.project.ridewave.RideApp.entities.WalletTransaction;
import com.example.project.ridewave.RideApp.repositories.WalletTransactionRepository;
import com.example.project.ridewave.RideApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }

}
