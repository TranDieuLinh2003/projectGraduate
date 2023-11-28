package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Movie;
import com.example.filmBooking.repository.PromotionRepository;
import com.example.filmBooking.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private PromotionRepository repository;


    @Override
    public List<BigDecimal> revenueInTheLast7DaysThanhXuan() {
        List<BigDecimal> thanhXuan = repository.revenueInTheLast7DaysThanhXuan();
        return thanhXuan;
    }

    @Override
    public List<BigDecimal> revenueInTheLast7DaysMyDinh() {
        List<BigDecimal> myDinh = repository.revenueInTheLast7DaysMyDinh();
        return myDinh;
    }

    @Override
    public List<BigDecimal> revenueInTheLast7DaysMipec() {
        List<BigDecimal> mipec = repository.revenueInTheLast7DaysMipec();
        return mipec;
    }

    @Override
    public List<Object> revenueTicket(Date startDate, Date endDate) {
        return repository.revenueTicket(startDate, endDate);
    }

    @Override
    public List<Object> revenueFood(Date startDate, Date endDate) {
        return repository.revenueFood(startDate, endDate);
    }

    @Override
    public List<Object> revenueMovie(Date fromDate, Date toDate) {
        return repository.revenueMovie(fromDate, toDate);
    }
}
