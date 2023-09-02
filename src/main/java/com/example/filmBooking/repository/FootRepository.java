package com.example.filmBooking.repository;

import com.example.filmBooking.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface FootRepository extends JpaRepository<Food, UUID> {
}
