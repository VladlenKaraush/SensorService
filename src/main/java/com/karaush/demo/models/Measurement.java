package com.karaush.demo.models;

import com.karaush.demo.validators.annotations.LocationConstraint;
import com.karaush.demo.validators.annotations.TemperatureConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @LocationConstraint(lowerLimit = -90, upperLimit = 90)
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @LocationConstraint(lowerLimit = -180, upperLimit = 180)
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    //in celsius
    @TemperatureConstraint
    @Column(name = "temperature", nullable = false)
    private Double temperature;

    public Measurement(){}

    public Measurement(double latitude, double longitude, double temperature){
        this.longitude = longitude;
        this.latitude = latitude;
        this.temperature = temperature;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getTemperature() { return temperature;}

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
