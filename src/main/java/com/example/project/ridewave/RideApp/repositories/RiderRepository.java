package com.example.project.ridewave.RideApp.repositories;

import com.example.project.ridewave.RideApp.entities.Rider;
import com.example.project.ridewave.RideApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByUser(User user);
}
