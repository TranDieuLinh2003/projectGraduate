package com.example.filmBooking.repository;

import com.example.filmBooking.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FootRepository extends JpaRepository<Food, String> {
}
