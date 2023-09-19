package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.SeatType;
import com.example.filmBooking.repository.SeatTypeRepository;
import com.example.filmBooking.service.SeatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Service
public class SeatTypeServiceImpl implements SeatTypeService {
    @Autowired
    private SeatTypeRepository seatTypeRepository;

    @Override
    public List<SeatType> getAll() {
        return seatTypeRepository.findAll();
    }

    @Override
    public SeatType save(SeatType seatType) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        seatType.setCode("ST" + value);
        return seatTypeRepository.save(seatType);
    }

    @Override
    public SeatType update(String id, SeatType seatType) {
        SeatType seatTypeNew = findById(id);
        seatTypeNew.setName(seatType.getName());
        seatTypeNew.setWeekdayPrice(seatType.getWeekdayPrice());
        seatTypeNew.setWeekendPrice(seatType.getWeekendPrice());
        seatTypeNew.setDescription(seatType.getDescription());
        return seatTypeRepository.save(seatTypeNew);
    }

    @Override
    public void delete(String id) {
        seatTypeRepository.delete(findById(id));
    }

    @Override
    public SeatType findById(String id) {
        return seatTypeRepository.findById(id).get();
    }
}
