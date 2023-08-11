package com.example.filmBooking.component.customer;

import com.example.filmBooking.model.Customer;
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
}
