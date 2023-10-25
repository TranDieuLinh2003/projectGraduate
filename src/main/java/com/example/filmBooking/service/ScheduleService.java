package com.example.filmBooking.service;

import com.example.filmBooking.model.Movie;
import com.example.filmBooking.model.Schedule;

import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface ScheduleService {
    List<Schedule> findAll();

    String save(Schedule schedule);

    Schedule update(String id, Schedule schedule);

    void delete(String id);

    Schedule findById(String id);

    List<Schedule> findByNameContains(String name, LocalDate startAt, String movieName);

    List<String> getStart_At(String movieId, String cinemaId);

    List<String> getStart_At_Time(String movieId, String cinemaId, String start_at);

    List<Schedule> getSchedule(String movieId, String cinemaId, String startAt, String startTime);
}
