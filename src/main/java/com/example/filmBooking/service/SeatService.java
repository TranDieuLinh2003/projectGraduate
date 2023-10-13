package com.example.filmBooking.service;

import com.example.filmBooking.model.Seat;

import java.util.List;


public interface SeatService {
    List<Seat> getAll();

    Seat save(Integer lineNumber, Integer number, String idRoom);

    Seat update(String id, Seat seat);

    void delete(String id);

    Seat findById(String id);

    List<SeatDTO> getSeatsByScheduleId(String scheduleId);

    List<Object[]> getSeatsByCustomerId(String customerId);
}
