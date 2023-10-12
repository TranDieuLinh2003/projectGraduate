package com.example.filmBooking.service;

import com.example.filmBooking.model.Schedule;

import java.sql.Time;
import java.text.ParseException;
import java.util.List;


public interface ScheduleService {
    List<Schedule> findAll();

    String save(Schedule schedule);

    Schedule update(String id, Schedule schedule);

    void delete(String id);

    Schedule findById(String id);

    List<Schedule> findByNameContains(String name);

    List<String> getStart_At(String movieId, String cinemaId);

    List<String> getStart_At_Time(String movieId, String cinemaId, String start_at);
}
