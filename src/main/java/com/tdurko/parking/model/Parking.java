package com.tdurko.parking.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * Created by Tomek on 2018-05-11.
 */
@Entity
public class Parking {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private boolean vip;

    private boolean parks;

    private double payment;

    private long duration;

    private LocalDateTime parkingStart;

    private LocalDateTime parkingStop;

    private Currency currency;

    public Parking()
    {}

    public Parking(boolean vip)
    {
        this.vip = vip;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public boolean isParks() {
        return parks;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public double getPayment() {
        return payment;
    }

    public void setParkingStop(LocalDateTime parkingStop)
    {
        this.parkingStop=parkingStop;
    }
    public LocalDateTime getParkingStop()
    {
        return parkingStop;
    }
    public LocalDateTime getParkingStart()
    {
        return parkingStart;
    }

    public void setParkingStart(LocalDateTime parkingStart) {
        this.parkingStart=parkingStart;
    }

    public void setDuration(long duration)
    {
        this.duration=duration;
    }

    public long getDuration()
    {
        return duration;
    }
    public void setParks(boolean parks)
    {
        this.parks=parks;
    }

    public boolean getParks()
    {
        return parks;
    }


}
