package com.example.filmBooking.repository;

import com.example.filmBooking.model.Rank;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RankRepository extends JpaRepository<Rank, String> {
}
