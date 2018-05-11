package com.tdurko.parking.controller;

import com.tdurko.parking.model.Parking;
import com.tdurko.parking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Tomek on 2018-05-11.
 */
@RestController
@RequestMapping("/parking")
public class ParkingController {

    private ParkingService parkingService;

    public ParkingController(ParkingService parkingService)
    {
        this.parkingService= parkingService;
    }

    @PostMapping
    @RequestMapping("/start")
    ResponseEntity startParkingMeter(@Valid @RequestBody Parking input, BindingResult bindingResult)
    {
        ResponseEntity responseEntity;
        if(bindingResult.hasErrors())
        {
            responseEntity=new ResponseEntity("Bad parking request", HttpStatus.BAD_REQUEST);
        }
        else
        {
            responseEntity= new ResponseEntity(input, HttpStatus.OK);
            parkingService.addParkingMeter(input);

        }
        return  responseEntity;
    }

    @PutMapping
    @RequestMapping("/stop/{parkingId}")
    ResponseEntity stopParkingMeter(@PathVariable Long parkingId)
    {

        Parking update = parkingService.getParking(parkingId).get();
        update.setParkingStop();
        update.setParks(false);
        parkingService.updateParkingMeter(update);
        return new ResponseEntity(update, HttpStatus.OK);

    }
}
