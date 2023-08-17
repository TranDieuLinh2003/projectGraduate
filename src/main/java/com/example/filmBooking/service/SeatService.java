package com.example.filmBooking.service;

import com.example.filmBooking.model.Rank;
import com.example.filmBooking.model.Seat;

import java.util.List;
import java.util.UUID;

public interface SeatService {
    List<Seat> getAll();

    Seat save(Seat seat);

    Seat update(UUID id, Seat seat);

    void delete(UUID id);

    Seat findById(UUID id);
}
