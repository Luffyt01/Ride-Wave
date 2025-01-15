package com.example.project.ridewave.RideApp.repositories;

import com.example.project.ridewave.RideApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
