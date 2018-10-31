package com.karaush.demo.controllers;

import com.karaush.demo.models.Record;
import com.karaush.demo.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SensorController {

    private static final int recordsToKeep = 5;

    private final SensorRepository repository;

    @Autowired
    public SensorController(SensorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @PostMapping("/records")
    public void saveRecord(@RequestBody Record record){

        repository.saveAndFlush(record);
        if(repository.count() > recordsToKeep){
            repository.dropLast();
        }
    }


    @GetMapping("/records")
    public List<Record> getRecords(){
        return new ArrayList<>(repository.findAll());
    }
}
