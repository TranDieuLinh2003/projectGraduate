package com.example.filmBooking.service;

import com.example.filmBooking.model.Movie;

import java.util.List;
import java.util.UUID;

public interface MovieService {
    List<Movie> fillAll();

    Movie save(Movie movie);

//    Movie update(UUID id, Movie Movie);

    void delete(UUID id);

    Movie findById(UUID id);

    void exportExcel();

    Movie readExcel();

    List<Movie> showPhimDangChieu();
    List<Movie> showPhimSapChieu();
}
