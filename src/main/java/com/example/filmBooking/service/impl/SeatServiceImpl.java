package com.example.filmBooking.service.impl;


import com.example.filmBooking.model.Seat;
import com.example.filmBooking.repository.SeatRepository;
import com.example.filmBooking.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatRepository seatRepository;
    @Override
    public List<Seat> getAll() {
        return seatRepository.findAll();
    }

    @Override
    public Seat save(Seat seat) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        seat.setCode("code_" + value);
        return seatRepository.save(seat);

    }

    @Override
    public Seat update(UUID id, Seat seat) {
        Seat seatNew = findById(id);
        seatNew.setNumber(seat.getNumber());
        seatNew.setLine(seat.getLine());
        seatNew.setStatus(seat.getStatus());
        seatNew.setDescription(seat.getDescription());
        return seatRepository.save(seatNew);
    }

    @Override
    public void delete(UUID id) {
        seatRepository.delete(findById(id));
    }

    @Override
    public Seat findById(UUID id) {
        return seatRepository.findById(id).get();
    }
}
