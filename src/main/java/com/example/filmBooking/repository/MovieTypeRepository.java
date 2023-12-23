package com.example.filmBooking.repository;

import com.example.filmBooking.model.MovieType;
import com.example.filmBooking.model.Performer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieTypeRepository extends JpaRepository<MovieType, String> {
    List<MovieType> findByNameContains(String keyCode);
}
