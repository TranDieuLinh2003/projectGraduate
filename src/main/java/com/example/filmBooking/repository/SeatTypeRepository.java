package com.example.filmBooking.repository;

import com.example.filmBooking.model.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SeatTypeRepository extends JpaRepository<SeatType, String> {
}
