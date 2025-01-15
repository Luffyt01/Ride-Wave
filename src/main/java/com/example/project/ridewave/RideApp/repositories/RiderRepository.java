package com.example.project.ridewave.RideApp.repositories;

import com.example.project.ridewave.RideApp.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
}
