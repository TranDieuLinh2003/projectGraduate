package com.example.filmBooking.service;

import com.example.filmBooking.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodService {
    List<Food> fillAll();

    Page<Food> findAll(Integer curentPage);

    Food save(Food food);

    Food update(String id, Food food);

    void delete(String id);

    Food findById(String id);

    Pageable pageFood(Integer pageNumber);

    Page<Food>findByNameContains(String keyword, Integer currentPage);
}
