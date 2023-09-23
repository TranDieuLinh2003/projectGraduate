package com.example.filmBooking.service;

import com.example.filmBooking.model.Movie;

import java.util.List;


public interface MovieService {
    List<Movie> fillAll();

    Movie save(Movie movie);

    Movie update(String id, Movie Movie);

    void delete(String id);

    Movie findById(String id);

    void exportExcel();

    Movie readExcel();

    List<Movie> showPhimDangChieu();
    List<Movie> showPhimSapChieu();
}
