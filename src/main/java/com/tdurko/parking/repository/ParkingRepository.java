package com.tdurko.parking.repository;

import com.tdurko.parking.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Tomek on 2018-05-11.
 */

public interface ParkingRepository extends JpaRepository<Parking, Long> {
}
