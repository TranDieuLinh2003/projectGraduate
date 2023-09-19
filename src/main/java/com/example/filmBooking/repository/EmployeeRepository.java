package com.example.filmBooking.repository;

import com.example.filmBooking.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
