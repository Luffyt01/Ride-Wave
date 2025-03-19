package com.example.project.ridewave.RideApp.entities;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_driver_vehicle_id", columnList = "vehicleId")
})
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double rating;
    private Boolean available;
    private String vehicleId;


    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point currentLocation;
}
