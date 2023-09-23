package com.example.filmBooking.controller;

import com.example.filmBooking.model.Customer;
import com.example.filmBooking.service.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/filmbooking")
@Controller
public class LoginController {

    @Autowired
    private CustomerServiceImpl service;



    @GetMapping("/dangnhap")
    public String dangNhap() {
        return "users/DangNhap";
    }

    @GetMapping("/dangky")
    public String dangky( Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "users/DangKy";
    }

    @PostMapping("/custom/save")
    public String dangkyKhachHang(@Valid Customer customer, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "users/DangKy";
        }
        service.save(customer);
        return "redirect:/filmbooking/dangky";

    }



}
