package com.tdurko.parking.service;

import com.tdurko.parking.model.Parking;
import com.tdurko.parking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Tomek on 2018-05-11.
 */
@Service
public class ParkingService {
    @Autowired
    private ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository)
    {
        this.parkingRepository=parkingRepository;
    }

    public Optional<Parking> getParking(long id)
    {
        return parkingRepository.findById(id);
    }
    public Parking addParkingMeter(Parking parking)
    {
        return parkingRepository.save(parking);
    }

    public Parking updateParkingMeter(Parking parking)
    {
        return parkingRepository.save(parking);
    }
}
