package com.example.project.ridewave.RideApp.services.impl;

import com.example.project.ridewave.RideApp.services.DistanceService;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DistanceServiceImpl implements DistanceService {

    private static final String OSRM_API_BASE_URL = "http://router.project-osrm.org/route/v1/driving/";

    @Override
    public double calculateDistance(Point src, Point dest) {
        // Call the third party API called OSRM to fetch the distance

        try {
            String dist = src.getX()+","+src.getY()+";"+dest.getX()+","+dest.getY();

            OsrmResponseDTO responseDTO = RestClient.builder()
                    .baseUrl(OSRM_API_BASE_URL)
                    .build()
                    .get()
                    .uri(dist)
                    .retrieve()
                    .body(OsrmResponseDTO.class);

            return responseDTO.getRoutes().get(0).getDistance() / 1000.0;
        } catch (Exception e){
            throw new RuntimeException("Error getting data from OSRM: "+e.getMessage());
        }

    }
}

@Data
class OsrmResponseDTO{
    private List<OsrmRoute> routes;
}

@Data
class OsrmRoute{
    private double distance;
}
