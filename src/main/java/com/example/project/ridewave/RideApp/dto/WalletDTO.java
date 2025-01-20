package com.example.project.ridewave.RideApp.dto;

import com.example.project.ridewave.RideApp.entities.User;
import com.example.project.ridewave.RideApp.entities.WalletTransaction;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO {

    private Long id;

    private UserDTO user;

    private Double balance;

    private List<WalletTransactionDTO> transactions;
}
