package com.example.filmBooking.service;

import com.example.filmBooking.model.Seat;
import com.example.filmBooking.model.SeatType;

import java.util.List;
import java.util.UUID;

public interface SeatTypeService {
    List<SeatType> getAll();

    SeatType save(SeatType seatType);

    SeatType update(UUID id, SeatType seatType);

    void delete(UUID id);

    SeatType findById(UUID id);
}
