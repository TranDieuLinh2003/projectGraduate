package com.example.filmBooking.controller;

import com.example.filmBooking.model.Movie;
import com.example.filmBooking.repository.MovieRepository;
import com.example.filmBooking.service.impl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/filmbooking")
public class FilmBookingController {
    @Autowired
    private MovieRepository repo;

    @Autowired
    private MovieServiceImpl service;


    @GetMapping("/trangchu")
    public String showFilm(Model model) {
        List<Movie> listmovie = (List<Movie>) repo.findAllBy();
        model.addAttribute("listmovie", listmovie);

        List<Movie> listmovie1 = (List<Movie>) repo.findAllBy1();
        model.addAttribute("listmovie1", listmovie1);
        return "admin/FilmBooking";
    }

    @GetMapping("/phimchieu")
    public String showPhimChieu(){
        return "admin/Phim";
    }
    @GetMapping("/lichchieu")
    public String showLichChieu(){
        return "admin/LichChieu";
    }
}
