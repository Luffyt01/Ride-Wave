package com.example.project.ridewave.RideApp.repositories;

import com.example.project.ridewave.RideApp.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
}
