package com.example.filmBooking.controller;

import com.example.filmBooking.model.Bill;
import com.example.filmBooking.model.Cinema;
import com.example.filmBooking.service.BillService;
import com.example.filmBooking.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private BillService billService;
    
    @GetMapping("/tong-quan")
    public String home(Model model, @Param(value = "id") String id) {
        List<Cinema> listCinema = cinemaService.fillAll();
        model.addAttribute("listCinema", listCinema);
        model.addAttribute("id", id);
        List<BigDecimal> thongKe7ngay = billService.revenueInTheLast7Days(id);
        model.addAttribute("thongKe7", thongKe7ngay);
        return "admin/home";
    }
}
