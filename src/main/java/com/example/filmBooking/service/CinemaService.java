package com.example.filmBooking.service;

import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.model.Rank;

import java.util.List;
import java.util.UUID;

public interface CinemaService {
    List<Cinema> fillAll();


    Cinema save(Cinema cinema);

    Cinema update(UUID id, Cinema cinema);

    void delete(UUID id);

    Cinema findById(UUID id);

}
