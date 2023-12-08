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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.filmBooking.model.Room;

public interface ScheduleService {
    List<Schedule> findAll();

    String save(Schedule schedule);

    Schedule update(String id, Schedule schedule);
    Schedule update1(String id, Schedule schedule);

    void delete(String id);

    Schedule findById(String id);


    List<String> getStart_At(String movieId, String cinemaId);

    List<Object[]> getStart_At_Time(String movieId, String cinemaId, String start_at);

    List<Schedule> getSchedule(String movieId, String cinemaId, String startAt, String startTime, String nameRoom);

    List<Schedule> getSchedule1( String movieName, String startAt, String nameRoom);

    Page<Schedule> getAll(Integer currentPage);

    Pageable pageSchedule(Integer pageNumber);
    
    Page<Schedule> searchSchedule (String name, LocalDate startAt, String movieName, Integer startTime, Integer endTime, String status, Integer currentPage);

    List<Schedule> generateSchedule(List<String> listRoom, List<String> listMovie, LocalDateTime startTime, LocalDateTime endTime);


}
