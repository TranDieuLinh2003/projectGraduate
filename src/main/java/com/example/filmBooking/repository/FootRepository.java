package com.example.filmBooking.repository;

import com.example.filmBooking.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FootRepository extends JpaRepository<Food, String> {
    Page<Food> findByNameContains(String keyword, Pageable pageable);

//    List<Food> findAll(Integer curentPage);
}
