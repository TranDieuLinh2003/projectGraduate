package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Customer;
import com.example.filmBooking.model.Rank;
import com.example.filmBooking.repository.CustomerRepository;
import com.example.filmBooking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Override
    public List<Customer> fillAll() {
        return repository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        customer.setCode("code_" + value);
        return repository.save(customer);
    }

    @Override
    public Customer update(UUID id, Customer customer) {
        Customer customerNew = findById(id);
        customerNew.setName(customer.getName());
        customerNew.setPoint(customer.getPoint());
//        customerNew.setDescription(rank.getDescription());
        return repository.save(customerNew);
    }

    @Override
    public Customer findById(UUID id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(UUID id) {
        repository.delete(findById(id));
    }

    @Override
    public String soSanhPoint(List<Rank> listRank, UUID id) {
        Customer customer= findById(id);
        String tenRank= "";
        for (Rank rank : listRank) {
            if(customer.getPoint()< rank.getPoint()){
//                tenRank= rank.getName();
                System.out.println("điểm KH: "+customer.getPoint());
                System.out.println("diểm ss: "+rank.getPoint());
            }
        }
        System.out.println("rank lafaaa: "+tenRank);
        return tenRank;
    }
}
