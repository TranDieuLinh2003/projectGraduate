package com.example.filmBooking.repository;

import com.example.filmBooking.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
}
