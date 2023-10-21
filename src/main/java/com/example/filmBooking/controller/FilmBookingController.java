package com.example.filmBooking.controller;

import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.model.Customer;
import com.example.filmBooking.model.Movie;
import com.example.filmBooking.model.dto.DtoMovie;
import com.example.filmBooking.repository.CinemaRepository;
import com.example.filmBooking.repository.ScheduleRepository;
import com.example.filmBooking.service.impl.CinemaServiceImpl;
import com.example.filmBooking.service.impl.MovieServiceImpl;
import com.example.filmBooking.service.impl.ScheduleServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
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

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @Autowired
    private CinemaRepository repository;

    //    @GetMapping("/trangchu")
//    public String getAllPosts(Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Customer customer = (Customer) session.getAttribute("customer");
//        model.addAttribute("customer", customer);
////      phim đang chiếu
//        List<DtoMovie> listmovie = (List<DtoMovie>) service.showPhimDangChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
//        model.addAttribute("listmovie", listmovie);
//
////      phim sap chiếu
//        List<DtoMovie> listmovie1 = (List<DtoMovie>) service.showPhimSapChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
//        model.addAttribute("listmovie1", listmovie1);
//
////
//        return "users/FilmBooking";
//    }
    @GetMapping("/trangchu")
    public String getAllPosts(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        model.addAttribute("customer", customer);
        // Phim đang chiếu
        List<DtoMovie> listmovie = (List<DtoMovie>) service.showPhimDangChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie", listmovie);
        // Phim sắp chiếu
        List<DtoMovie> listmovie1 = (List<DtoMovie>) service.showPhimSapChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie1", listmovie1);
        //
        return "users/FilmBooking";
    }

    @GetMapping("/movie/edit/{id}")
    public String chiTietPhim(@PathVariable("id") String id, Model model) {
        Movie movie = service.findById(id);
        model.addAttribute("movie", movie);

        return "users/ChiTietPhim";

    }


    @GetMapping("/phimchieu")
    public String showPhimChieu(Model model) {
        List<DtoMovie> listmovie = (List<DtoMovie>) service.showPhimDangChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie", listmovie);

//      phim sap chiếu
        List<DtoMovie> listmovie1 = (List<DtoMovie>) service.showPhimSapChieu().stream().map(movie -> modelMapper.map(movie, DtoMovie.class)).collect(Collectors.toList());
        model.addAttribute("listmovie1", listmovie1);

//      rap
//        List<Cinema> listcinema = (List<Cinema>) cinemaService.fillAll();
//        model.addAttribute("listcinema", listcinema);


        return "users/Phim";
    }

    @GetMapping("/lichchieu")
    public String showLichChieu() {
        return "users/LichChieu";
    }

    @GetMapping("/chitietphim")
    public String showChiTietPhim() {
        return "users/ChiTietPhim";
    }


}
