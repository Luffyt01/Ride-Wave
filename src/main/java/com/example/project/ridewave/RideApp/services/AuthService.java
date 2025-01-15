package com.example.project.ridewave.RideApp.services;

import com.example.project.ridewave.RideApp.dto.DriverDTO;
import com.example.project.ridewave.RideApp.dto.SignupDTO;
import com.example.project.ridewave.RideApp.dto.UserDTO;

public interface AuthService {
    String login(String email, String password);

    UserDTO signup(SignupDTO signupDTO);

    DriverDTO onboardNewDriver(Long userId);
}
