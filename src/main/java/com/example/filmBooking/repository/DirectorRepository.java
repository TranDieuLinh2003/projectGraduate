package com.example.filmBooking.repository;

import com.example.filmBooking.model.Director;
import com.example.filmBooking.model.Performer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DirectorRepository extends JpaRepository<Director, String> {
    List<Director> findByNameContains(String keyCode);
}
