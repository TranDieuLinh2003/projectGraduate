package com.example.filmBooking.repository;

import com.example.filmBooking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket, String> {
    String str_findBySchedule =
            "select * from ticket where  (schedule_id = (:scheduleId) )";

    @Query(nativeQuery = true, value = str_findBySchedule)
    List<Ticket> findBySchedule(@Param("scheduleId") String scheduleId);
}
