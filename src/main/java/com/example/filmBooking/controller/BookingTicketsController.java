package com.example.filmBooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/filmbooking")
public class BookingTicketsController {
    @GetMapping("/datve")
    public String webAll() {
        return "users/DatVe";
    }
}
