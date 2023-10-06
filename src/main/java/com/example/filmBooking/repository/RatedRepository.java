package com.example.filmBooking.repository;

import com.example.filmBooking.model.Rated;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatedRepository extends JpaRepository<Rated, String> {
}
