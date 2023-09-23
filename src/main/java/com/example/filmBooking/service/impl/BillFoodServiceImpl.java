package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.BillFood;
import com.example.filmBooking.repository.BillFoodRepository;
import com.example.filmBooking.service.BillFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Service
public class BillFoodServiceImpl implements BillFoodService {

    @Autowired
    private BillFoodRepository repository;

    @Override
    public List<BillFood> findAll() {
        return repository.findAll();
    }

    @Override
    public BillFood save(BillFood billFood) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
//        billFood.setCode("code_" + value);
        return repository.save(billFood);
    }

    @Override
    public BillFood update(String id, BillFood billFood) {
        BillFood BillFoodNew = findById(id);
//        BillFoodNew.setName(BillFood.getName());
//        BillFoodNew.setPoint(BillFood.getPoint());
//        BillFoodNew.setDescription(BillFood.getDescription());
        return repository.save(BillFoodNew);
    }

    @Override
    public BillFood findById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(String id) {
        repository.delete(findById(id));
    }
}
