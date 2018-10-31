package com.karaush.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SensorController {

    private final JpaRepository<Measurement, Long> repository;

    @Autowired
    public SensorController(JpaRepository<Measurement, Long> repository) {
        this.repository = repository;
    }

    @PostMapping("/measurements")
    public void saveMeasurement(@RequestBody Measurement measurement){

//        Measurement ms = new Measurement(measurement);
        //measurement.setId(123L);
        Measurement result = repository.saveAndFlush(measurement);
        System.out.println("qwe");
    }


    @GetMapping("/measurements")
    public List<Measurement> getMeasurements(){
        return new ArrayList<>(repository.findAll());
    }
}
