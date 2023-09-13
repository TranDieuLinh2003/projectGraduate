package com.example.filmBooking.repository;

import com.example.filmBooking.model.Movie;
import com.example.filmBooking.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

}
