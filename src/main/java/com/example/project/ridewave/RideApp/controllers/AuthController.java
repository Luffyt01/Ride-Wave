package com.example.project.ridewave.RideApp.controllers;

import com.example.project.ridewave.RideApp.dto.DriverDTO;
import com.example.project.ridewave.RideApp.dto.OnboardDriverDTO;
import com.example.project.ridewave.RideApp.dto.SignupDTO;
import com.example.project.ridewave.RideApp.dto.UserDTO;
import com.example.project.ridewave.RideApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignupDTO signupDTO){
        return new ResponseEntity<>(authService.signup(signupDTO), HttpStatus.CREATED);
    }

    @PostMapping("/onBoardNewDriver/{user_id}")
    ResponseEntity<DriverDTO> onBoardNewDriver(@PathVariable Long user_id, @RequestBody OnboardDriverDTO onboardDriverDTO){
        return new ResponseEntity<>(authService.
                onboardNewDriver(user_id, onboardDriverDTO.getVehicleId()), HttpStatus.CREATED);
    }

}
