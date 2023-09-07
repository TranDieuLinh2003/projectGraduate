package com.example.filmBooking.controller;

import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.model.dto.DtoMovie;
import com.example.filmBooking.service.impl.CinemaServiceImpl;
import com.example.filmBooking.service.impl.MovieServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/filmbooking")

public class FilmBookingController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MovieServiceImpl service;

    @Autowired
    private CinemaServiceImpl cinemaService;



    @RequestMapping(value = "/trangchu", method = RequestMethod.GET)
    public String getAllPosts(Model model) {
//      phim đang chiếu
        List<DtoMovie> listmovie = (List<DtoMovie>) service.showPhimDangChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie", listmovie);
//      phim sap chiếu
        List<DtoMovie> listmovie1 = (List<DtoMovie>) service.showPhimSapChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie1", listmovie1);
//      rap
        List<Cinema> listcinema = (List<Cinema>) cinemaService.fillAll();
        model.addAttribute("listcinema", listcinema);


        return "users/FilmBooking";
    }

    @GetMapping("/phimchieu")
    public String showPhimChieu() {
        return "users/Phim";
    }

    @GetMapping("/lichchieu")
    public String showLichChieu() {
        return "users/LichChieu";
    }



}
