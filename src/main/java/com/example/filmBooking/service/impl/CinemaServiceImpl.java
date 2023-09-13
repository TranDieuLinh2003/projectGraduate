package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.repository.CinemaRepository;
import com.example.filmBooking.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
@Service
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private CinemaRepository repository;

    @Override
    public List<Cinema> fillAll() {

        return repository.findAll();
    }


    @Override
    public Cinema save(Cinema cinema) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        cinema.setCode("CFBK" + value);
        return repository.save(cinema);
    }

    @Override
    public Cinema update(UUID id, Cinema cinema) {
        Cinema customerNew = findById(id);
        customerNew.setName(cinema.getName());
        customerNew.setDescription(cinema.getDescription());
        customerNew.setAddress(cinema.getAddress());
//        customerNew.setDescription(rank.getDescription());
        return repository.save(customerNew);
    }

    @Override
    public Cinema findById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(UUID id) {
        repository.delete(findById(id));
    }

}
