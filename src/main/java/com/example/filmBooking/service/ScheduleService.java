package com.example.filmBooking.service;

import com.example.filmBooking.model.Schedule;
import com.example.filmBooking.model.dto.ScheduleDto;

import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;


public interface ScheduleService {
    List<Schedule> findAll();

    String save(Schedule schedule) throws ParseException;

    Schedule update(String id, Schedule schedule);

    void delete(String id);

    Schedule findById(String id);

    List<String> getTimeAt(String movieId, String cinemaId);
    List<String> getStartAt(String movieId, String cinemaId);
    List<String> getFinishAt(String movieId, String cinemaId);
//    List<ScheduleDto> getSchedules(String movieId, String cinemaId, String startAt, String roomId);

    List<Time> layThoiGian();

    long CheckConstraint(String id,
                         LocalDateTime from_startAt,
                         LocalDateTime to_startAt,
                         LocalDateTime from_finishAt,
                         LocalDateTime to_finishAt);

}
