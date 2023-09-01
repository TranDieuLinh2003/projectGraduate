package com.example.filmBooking.service;

import com.example.filmBooking.model.Movie;

import java.util.UUID;

public interface MovieService {
    Movie findById(UUID id);

}
