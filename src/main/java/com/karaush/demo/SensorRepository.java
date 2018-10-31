package com.karaush.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Measurement, Long> {

}
