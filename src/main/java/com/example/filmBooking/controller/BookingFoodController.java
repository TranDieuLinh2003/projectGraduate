package com.example.filmBooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/filmbooking")
@Controller
public class BookingFoodController {
    @GetMapping("/doan")
    public String dangNhap() {
        return "admin/DoAn";
    }
}
