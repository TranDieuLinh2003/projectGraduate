package com.example.filmBooking.service;

import com.example.filmBooking.model.Employee;

import java.util.List;


public interface EmployeeService {
    List<Employee> fillAll();

    Employee save(Employee employee);

    Employee update(String id, Employee employee);

    void delete(String id);

    Employee findById(String id);

}
