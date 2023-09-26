package com.example.filmBooking.repository;

import com.example.filmBooking.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    @Query(value = "SELECT TIMEDIFF(?1, ?2)", nativeQuery = true)
    List<Time> layKhoangTrong(Time start, Time end);

    @Query(value = "select*from schedule ORDER BY start_at ASC", nativeQuery = true)
    List<Schedule> findAll();

    @Query(value = "select *from schedule where room_id= ?1", nativeQuery = true)
    List<Schedule> findByRoom(String id);



}
