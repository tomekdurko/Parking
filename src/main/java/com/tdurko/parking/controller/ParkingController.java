package com.tdurko.parking.controller;

import com.tdurko.parking.model.Parking;
import com.tdurko.parking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by Tomek on 2018-05-11.
 */
@RestController
@RequestMapping("/parking")
public class ParkingController {
    @Autowired
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
            input.setParkingStart(LocalDateTime.now());
            input.setParks(true);
            responseEntity= new ResponseEntity(input, HttpStatus.OK);
            parkingService.addParkingMeter(input);

        }
        return  responseEntity;
    }

    @GetMapping
    @RequestMapping("/{parkingId}")
    ResponseEntity getParking(@PathVariable Long parkingId)
    {
        ResponseEntity responseEntity;
        Optional<Parking> parking = parkingService.getParking(parkingId);
        if (!parking.isPresent())
        {
            responseEntity= new ResponseEntity("No Parking transaction found for ID " + parkingId, HttpStatus.NOT_FOUND);
        }
        else
        {
            responseEntity= new ResponseEntity(parking, HttpStatus.OK);
        }
        return responseEntity;
    }

    @GetMapping
    @RequestMapping("/payment/{parkingId}")
    ResponseEntity getParkingPaymnet(@PathVariable Long parkingId)
    {
        ResponseEntity responseEntity;
        Optional<Parking> parking = parkingService.getParking(parkingId);
        if (!parking.isPresent())
        {
            responseEntity= new ResponseEntity("No Parking transaction found for ID " + parkingId, HttpStatus.NOT_FOUND);
        }
        else
        {
            responseEntity= new ResponseEntity("You have to pay: " + parking.get().getPayment() + " " +parking.get().getCurrency() + " for " +parking.get().getDuration() + " minutes of parking"  , HttpStatus.OK);
        }
        return responseEntity;
    }
    @PutMapping
    @RequestMapping("/stop/{parkingId}")
    ResponseEntity stopParkingMeter(@PathVariable Long parkingId)
    {
        ResponseEntity responseEntity;
        Optional<Parking> update = parkingService.getParking(parkingId);
        if(!update.isPresent())
            responseEntity = new ResponseEntity("No Parking transaction found for ID " + parkingId, HttpStatus.NOT_FOUND);
        else
        {
            Parking parking =update.get();
            parking.setParkingStop(LocalDateTime.now());
            parking.setParks(false);
            Duration duration = Duration.between(parking.getParkingStart(),parking.getParkingStop());
            long durationLong = Math.abs(duration.toMinutes());
            parking.setDuration(durationLong);

            if(durationLong<=60)
            {
                if(parking.isVip())
                    parking.setPayment(0);
                else
                    parking.setPayment(1);
            }
            else if (durationLong<=120)
            {
                if(parking.isVip())
                    parking.setPayment(2);
                else
                    parking.setPayment(3);
            }
            else
            {
                durationLong-=120;
                double lastHour=2;
                double sumVip=2;
                double sumNotVip=3;
                while(durationLong>0)
                {
                    if(parking.isVip())
                    {
                        lastHour*=1.2;
                        sumVip+=lastHour;
                    }
                    else
                        {
                            lastHour *= 1.5;
                            sumNotVip+=lastHour;
                    }
                    durationLong-=60;
                }
                if (parking.isVip())
                    parking.setPayment(sumVip);
                else
                    parking.setPayment(sumNotVip);

            }
            Locale locale = new Locale("us", "PL");
            parking.setCurrency(Currency.getInstance(locale));
            parkingService.updateParkingMeter(parking);
            responseEntity = new ResponseEntity(parking, HttpStatus.OK);
        }
        return responseEntity;


    }
}
