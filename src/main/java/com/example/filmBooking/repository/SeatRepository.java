package com.example.filmBooking.repository;

import com.example.filmBooking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {

    String str_findByPrimary =
            "select * from seat where  (room_id = (:roomId) )";
    @Query(nativeQuery = true, value = str_findByPrimary)
    List<Seat> findAllByRoom(@Param("roomId") UUID roomId);



}
