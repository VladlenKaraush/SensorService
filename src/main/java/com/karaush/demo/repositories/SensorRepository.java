package com.karaush.demo.repositories;

import com.karaush.demo.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Record, Long> {

    @Modifying
    @Query("delete from Record m where m.id in ?1")
    void deleteUsersWithIds(List<Integer> ids);

    //List<Record> getAllByIdOrderByCreatedDesc();

    @Modifying
    @Query(value = " delete from records where id in (select id from records m order by m.created desc offset 5)", nativeQuery = true)
    void dropLast();

    @Query(value = "select * from records m order by m.created desc", nativeQuery = true)
    List<Record> retrieveSorted();

}
