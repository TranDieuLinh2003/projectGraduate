package com.example.filmBooking.repository;

import com.example.filmBooking.model.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeatTypeRepository extends JpaRepository<SeatType, UUID> {
}
