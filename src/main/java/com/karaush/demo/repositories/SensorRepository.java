package com.karaush.demo.repositories;

import com.karaush.demo.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Measurement, Long> {

    @Modifying
    @Query("delete from Measurement m where m.id in ?1")
    void deleteUsersWithIds(List<Integer> ids);

    //List<Measurement> getAllByIdOrderByCreatedDesc();

    @Modifying
    @Query(value = " delete from measurements where id in (select id from measurements m order by m.created desc offset 5)", nativeQuery = true)
    void dropLast();

    @Query(value = "select * from measurements m order by m.created desc", nativeQuery = true)
    List<Measurement> retrieveSorted();

}
