package com.example.filmBooking.service.impl;

import com.example.filmBooking.model.Employee;
import com.example.filmBooking.repository.EmployeeRepository;
import com.example.filmBooking.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public List<Employee> fillAll() {
        return repository.findAll();
    }

    @Override
    public Employee save(Employee Employee) {
        Random generator = new Random();
        int value = generator.nextInt((100000 - 1) + 1) + 1;
        Employee.setCode("EM" + value);
        return repository.save(Employee);
    }

    @Override
    public Employee update(String id, Employee Employee) {
        Employee EmployeeNew = findById(id);
        EmployeeNew.setName(Employee.getName());
//        EmployeeNew.setPoint(Employee.getPoint());
//        EmployeeNew.setDescription(Employee.getDescription());
        return repository.save(EmployeeNew);
    }

    @Override
    public Employee findById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(String id) {
        repository.delete(findById(id));
    }
}
