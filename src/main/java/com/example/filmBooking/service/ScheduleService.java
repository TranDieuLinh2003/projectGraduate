package com.example.filmBooking.service;

import com.example.filmBooking.model.Schedule;

import java.util.List;
import java.util.UUID;

public interface ScheduleService {
    List<Schedule> fillAll();

    Schedule save(Schedule schedule);

    Schedule update(UUID id, Schedule schedule);

    void delete(UUID id);

    Schedule findById(UUID id);

}
