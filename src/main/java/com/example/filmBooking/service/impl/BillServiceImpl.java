package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Bill;
import com.example.filmBooking.repository.BillRepository;
import com.example.filmBooking.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository repository;
    @Autowired
    private testQR testQR;

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

    @Override
    public void delete(String id) {
        repository.delete(findById(id));
    }
}
