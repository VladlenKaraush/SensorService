package com.karaush.demo;

import com.karaush.demo.validators.annotations.LocationConstraint;
import com.karaush.demo.validators.annotations.TemperatureConstraint;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue()
    private Long id;

    @NotNull
    @LocationConstraint(lowerLimit = -90, upperLimit = 90)
    @Column(name = "latitude")
    private Double latitude;

    @NotNull
    @LocationConstraint(lowerLimit = -180, upperLimit = 180)
    @Column(name = "longitude")
    private Double longitude;

    //in celsius
    @NotNull
    @TemperatureConstraint
    @Column(name = "temperature")
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

    public void setId(Long id){
        this.id = id;
    }

}
