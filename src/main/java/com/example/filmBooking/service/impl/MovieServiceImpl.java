package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.model.Movie;
import com.example.filmBooking.repository.MovieRepository;
import com.example.filmBooking.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository repository;

    @Override
    public Movie findById(UUID id) {
        return repository.findById(id).get();
    }
}
