package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Food;
import com.example.filmBooking.repository.FootRepository;
import com.example.filmBooking.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FootRepository footRepository;
    @Override
    public List<Food> fillAll() {
        return footRepository.findAll();
    }

    @Override
    public Food save(Food food) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        food.setCode("code_food_" + value);
        return footRepository.save(food);
    }

    @Override
    public Food update(UUID id, Food food) {
        Food foods = findById(id);
        foods.setCode(food.getCode());
        foods.setDescription(food.getDescription());
        foods.setName(food.getName());
        foods.setPrice(food.getPrice());
        return footRepository.save(food);
    }

    @Override
    public void delete(UUID id) {
        footRepository.delete(findById(id));
    }

    @Override
    public Food findById(UUID id) {
        return footRepository.findById(id).get();
    }
}
