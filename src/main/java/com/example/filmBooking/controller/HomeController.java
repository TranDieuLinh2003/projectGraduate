package com.example.filmBooking.controller;

import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping
    public String home(Model model) {
        List<Cinema> listCinema = cinemaService.fillAll();
        model.addAttribute("listCinema", listCinema);
        return "admin/home";
    }
}
