package com.example.filmBooking.service;

import com.example.filmBooking.model.Schedule;

import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;


public interface ScheduleService {
    List<Schedule> findAll();

    String save(Schedule schedule) throws ParseException;

    Schedule update(String id, Schedule schedule);

    void delete(String id);

    Schedule findById(String id);

    List<Time> layThoiGian();

    long CheckConstraint(String id,
                         LocalDateTime from_startAt,
                         LocalDateTime to_startAt,
                         LocalDateTime from_finishAt,
                         LocalDateTime to_finishAt);

}
