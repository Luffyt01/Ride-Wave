package com.example.project.ridewave.RideApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderDTO {
    private UserDTO user;
    private Double rating;
}
