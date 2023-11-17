package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.*;
import com.example.filmBooking.repository.*;
import com.example.filmBooking.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Date;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository repository;

    @Autowired
    private ScheduleServiceImpl scheduleRepository;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private BillTicketRepository billTicketRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public List<Bill> findAll() {
        return repository.findAll();
    }

    @Override
    public Bill save(Bill bill) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        bill.setCode("Bill" + value);
        bill.setName("Bill" + bill.getDateCreate());
//        bill.setTotalMoney();
//        bill.setCinema();
        return repository.save(bill);
    }

    @Override
    public Bill update(String id, Bill bill) {
        Bill BillNew = findById(id);
//        BillNew.setName(Bill.getName());
//        BillNew.setPoint(Bill.getPoint());
//        BillNew.setDescription(Bill.getDescription());
        return repository.save(BillNew);
    }

    @Override
    public Bill findById(String id) {
        return repository.findById(id).get();
    }

//

    @Override
    public void delete(String id) {
        repository.delete(findById(id));
    }
    
    @Override
    public Page<Bill> findStatusZero(Integer pageNumber) {
        return repository.billStatusZero(pageBill(pageNumber));
    }

    @Override
    public Page<Bill> findStatusOne(Integer pageNumber) {
        return repository.billStatusOne(pageBill(pageNumber));
    }

    @Override
    public Pageable pageBill(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber -1, 5);
        return pageable;
    }

    @Override
    public Page<Bill> searchDateAndDate(Date startDate, Date endDate, Integer pageNumber) {
        return repository.findByDateCreateBetween(startDate, endDate, pageBill(pageNumber));
    }
    
    @Override
    public List<BigDecimal> revenueInTheLast7Days(String cinemaId) {
        return repository.revenueInTheLast7Days(cinemaId);
    }

    @Override
    public List<Object[]> listTop5Movie() {
        return repository.listTop5Movie();
    }
}
