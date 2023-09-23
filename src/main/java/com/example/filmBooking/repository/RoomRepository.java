package com.example.filmBooking.repository;

import com.example.filmBooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RoomRepository extends JpaRepository<Room, String> {
    String findNumberOfSeat = "select COUNT(*) from seat WHERE room_id =:id";

    @Query(value = findNumberOfSeat, nativeQuery = true)
    Integer findNumber(String id);
}
