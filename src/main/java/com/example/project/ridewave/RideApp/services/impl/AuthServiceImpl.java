package com.example.project.ridewave.RideApp.services.impl;

import com.example.project.ridewave.RideApp.dto.DriverDTO;
import com.example.project.ridewave.RideApp.dto.SignupDTO;
import com.example.project.ridewave.RideApp.dto.UserDTO;
import com.example.project.ridewave.RideApp.entities.Driver;
import com.example.project.ridewave.RideApp.entities.User;
import com.example.project.ridewave.RideApp.entities.enums.Role;
import com.example.project.ridewave.RideApp.exceptions.ResourceNotFoundException;
import com.example.project.ridewave.RideApp.exceptions.RuntimeConflictException;
import com.example.project.ridewave.RideApp.repositories.UserRepository;
import com.example.project.ridewave.RideApp.services.AuthService;
import com.example.project.ridewave.RideApp.services.DriverService;
import com.example.project.ridewave.RideApp.services.RiderService;
import com.example.project.ridewave.RideApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;


    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    @Transactional
    public UserDTO signup(SignupDTO signupDTO) {
         User user = userRepository.findByEmail(signupDTO.getEmail()).orElse(null);
         if (user != null){
             throw new RuntimeConflictException("Cannot signup, User already exists with email "+signupDTO.getEmail());
         }


        User mappedUser = modelMapper.map(signupDTO, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(mappedUser);

        // create user related entities
        riderService.createNewRider(savedUser);

        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public DriverDTO onboardNewDriver(Long userId, String vehicleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+userId));

        if (user.getRoles().contains(Role.DRIVER)) {
            throw new RuntimeConflictException("User with id "+userId+" is already a Driver");
        }

        Driver createDriver = Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();

        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);
        Driver savedDriver = driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver, DriverDTO.class);
    }
}
