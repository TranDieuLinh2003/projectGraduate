package com.example.filmBooking.repository;

import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CinemaRepository  extends JpaRepository<Cinema, String> {
}
