package com.karaush.demo.controllers;

import com.karaush.demo.models.Measurement;
import com.karaush.demo.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SensorController {

    private final SensorRepository repository;

    @Autowired
    public SensorController(SensorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @PostMapping("/measurements")
    public void saveMeasurement(@RequestBody Measurement measurement){

        //
        if(repository.count() > 10){
            repository.dropLast();
        }
        repository.saveAndFlush(measurement);

        List<Measurement> all = repository.retrieveSorted();
        System.out.println("got all");
        repository.dropLast();
        all = repository.retrieveSorted();
    }


    @GetMapping("/measurements")
    public List<Measurement> getMeasurements(){
        return new ArrayList<>(repository.findAll());
    }
}
