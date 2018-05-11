package com.tdurko.parking.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Tomek on 2018-05-11.
 */
@Entity
@Data
public class Parking {
    @Id
    @GeneratedValue
    private long id;

    private Date parkingStart;

    private Date parkingStop;

    public void setParkingStop(Date parkingStop)
    {
        this.parkingStop=parkingStop;
    }

    private boolean parks;

    public void setParks(boolean parks)
    {
        this.parks=parks;
    }

}
